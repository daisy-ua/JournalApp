package com.daisy.journalapp.authentication.data.repository

import com.daisy.journalapp.authentication.data.mapper.toUserProfile
import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import com.daisy.journalapp.core.presentation.utils.AuthError
import com.daisy.journalapp.core.presentation.utils.NetworkError
import com.daisy.journalapp.core.presentation.utils.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {

    override suspend fun signUpWithEmail(userProfile: UserProfile): AuthResponse {
        return try {
            val result = auth.createUserWithEmailAndPassword(userProfile.email!!, userProfile.password!!).await()
            val firebaseUser = result.user
                ?: return Response.Error(AuthError.UnknownAuthError)

            val updateResult = updateUserProfile(firebaseUser, userProfile)
            updateResult
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Response.Error(AuthError.InvalidCredentials)
        } catch (e: FirebaseAuthUserCollisionException) {
            Response.Error(AuthError.UserAlreadyExists)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Response.Error(NetworkError.NoInternetConnection)
                is TimeoutException -> Response.Error(NetworkError.TimeoutError)
                else -> Response.Error(NetworkError.UnknownNetworkError)
            }
        }
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): AuthResponse {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
                ?: return Response.Error(AuthError.UnknownAuthError)
            Response.Success(firebaseUser.toUserProfile())
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Response.Error(AuthError.InvalidCredentials)
        } catch (e: FirebaseAuthInvalidUserException) {
            Response.Error(AuthError.UserNotFound)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Response.Error(NetworkError.NoInternetConnection)
                is TimeoutException -> Response.Error(NetworkError.TimeoutError)
                else -> Response.Error(NetworkError.UnknownNetworkError)
            }
        }
    }

    private suspend fun updateUserProfile(
        firebaseUser: FirebaseUser,
        userProfile: UserProfile
    ): AuthResponse {
        return try {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(userProfile.username)
                .build()

            firebaseUser.updateProfile(profileUpdates).await()

            Response.Success(userProfile.copy(uid = firebaseUser.uid))
        } catch (e: Exception) {
            Response.Error(AuthError.UnknownAuthError)
        }
    }
}