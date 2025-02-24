package com.daisy.journalapp.authentication.domain.repository

import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

interface AuthRepository {

    suspend fun signUpWithEmail(userProfile: UserProfile): AuthResponse

    suspend fun signInWithEmail(email: String, password: String): AuthResponse

    suspend fun signInWithGoogle(googleIdTokenCredential: GoogleIdTokenCredential): AuthResponse
}