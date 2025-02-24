package com.daisy.journalapp.authentication.ui.credential

import android.content.Context
import android.util.Log
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.daisy.journalapp.BuildConfig
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class CredentialManagerWrapper(
    private val context: Context,
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun getStoredCredentials(): GetCredentialResult {
        return fetchCredentials(GetCredentialRequest(listOf(GetPasswordOption())))
    }

    suspend fun saveCredentials(id: String, password: String): SaveCredentialResult {
        return try {
            credentialManager.createCredential(
                context,
                CreatePasswordRequest(id = id, password = password)
            )
            SaveCredentialResult.Success
        } catch (e: Exception) {
            handleCreateCredentialException(e)
        }
    }

    suspend fun signInWithGoogle(): GetCredentialResult {
        val googleIdOption = GetSignInWithGoogleOption.Builder(BuildConfig.WEB_CLIENT_ID).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        return fetchCredentials(request)
    }

    private suspend fun fetchCredentials(request: GetCredentialRequest): GetCredentialResult {
        return try {
            val credentialResponse = credentialManager.getCredential(context, request)
            processCredential(credentialResponse.credential)
        } catch (e: Exception) {
            handleGetCredentialException(e)
        }
    }

    private fun processCredential(credential: Credential): GetCredentialResult {
        return when (credential) {
            is PasswordCredential -> GetCredentialResult.Success(credential)
            is CustomCredential -> processCustomCredential(credential)
            else -> GetCredentialResult.Failure
        }
    }

    private fun processCustomCredential(credential: CustomCredential): GetCredentialResult {
        return if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                GetCredentialResult.Success(googleIdTokenCredential)
            } catch (e: GoogleIdTokenParsingException) {
                Log.e("CredentialManager", "Google ID Token parsing failed: ${e.message}")
                GetCredentialResult.Failure
            }
        } else {
            GetCredentialResult.Failure
        }
    }

    private fun handleGetCredentialException(e: Exception): GetCredentialResult {
        return when (e) {
            is GetCredentialCancellationException -> GetCredentialResult.Cancelled
            is NoCredentialException -> GetCredentialResult.NoCredentials
            is GetCredentialException -> {
                Log.e("CredentialManager", "Credential retrieval failed: ${e.message}")
                GetCredentialResult.Failure
            }

            else -> GetCredentialResult.Failure
        }
    }

    private fun handleCreateCredentialException(e: Exception): SaveCredentialResult {
        return when (e) {
            is CreateCredentialCancellationException -> SaveCredentialResult.Cancelled
            is CreateCredentialException, is NullPointerException -> {
                Log.e("CredentialManager", "Credential saving failed: ${e.message}")
                SaveCredentialResult.Failure
            }

            else -> SaveCredentialResult.Failure
        }
    }
}