package com.daisy.journalapp.authentication.domain.repository

import com.daisy.journalapp.authentication.domain.model.UserProfile

sealed interface AuthResponse {

    data class Success(val userProfile: UserProfile): AuthResponse

    data class Error(val message: String): AuthResponse
}