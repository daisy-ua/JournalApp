package com.daisy.journalapp.authentication.domain.repository

import com.daisy.journalapp.authentication.domain.model.UserProfile

interface AuthRepository {

    suspend fun signUpWithEmail(userProfile: UserProfile): AuthResponse

    suspend fun signInWithEmail(email: String, password: String): AuthResponse
}