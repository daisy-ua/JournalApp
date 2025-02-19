package com.daisy.journalapp.authentication.ui.credential

import android.content.Context
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException

class CredentialManagerWrapper(
    private val context: Context,
) {
    private val credentialManager = CredentialManager.create(context)

    suspend fun getStoredCredentials(): GetCredentialResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = context,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )
            val credentials = credentialResponse.credential as? PasswordCredential
                ?: return GetCredentialResult.Failure

            GetCredentialResult.Success(UserCredentials(credentials.id, credentials.password))
        } catch (e: GetCredentialCancellationException) {
            GetCredentialResult.Cancelled
        } catch (e: NoCredentialException) {
            GetCredentialResult.NoCredentials
        } catch (e: GetCredentialException) {
            GetCredentialResult.Failure
        }
    }

    suspend fun saveCredentials(credential: UserCredentials): SaveCredentialResult {
        return try {
            credentialManager.createCredential(
                context = context,
                request = CreatePasswordRequest(
                    id = credential.id,
                    password = credential.password!!
                )
            )

            SaveCredentialResult.Success(UserCredentials(credential.id))
        } catch (e: CreateCredentialCancellationException) {
            SaveCredentialResult.Cancelled
        } catch (e: CreateCredentialException) {
            SaveCredentialResult.Failure
        } catch (e: NullPointerException) {
            SaveCredentialResult.Failure
        }
    }
}