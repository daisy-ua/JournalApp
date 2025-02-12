package com.daisy.journalapp.authentication.data.repository

import com.daisy.journalapp.authentication.data.mapper.toUserProfile
import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {

    override suspend fun signUpWithEmail(
        userProfile: UserProfile,
        password: String
    ): AuthResponse {
        return try {
            val result = auth.createUserWithEmailAndPassword(userProfile.email!!, password).await()
            val firebaseUser = result.user
                ?: return AuthResponse.Error("User authentication failed, no user found.")

            val updateResult = updateUserProfile(firebaseUser, userProfile)
            updateResult
        } catch (e: Exception) {
            AuthResponse.Error(
                message = e.message ?: "Cannot sign-up."
            )
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResponse {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
                ?: return AuthResponse.Error("User authentication failed, no user found.")
            AuthResponse.Success(firebaseUser.toUserProfile())
        } catch (e: Exception) {
            AuthResponse.Error(
                message = e.message ?: "Cannot sign-in."
            )
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

            AuthResponse.Success(userProfile.copy(uid = firebaseUser.uid))
        } catch (e: Exception) {
            AuthResponse.Error(e.message ?: "Profile update failed.")
        }
    }
}