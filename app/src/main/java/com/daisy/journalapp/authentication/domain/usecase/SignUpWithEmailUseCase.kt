package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(userProfile: UserProfile, password: String): AuthResponse {
        return authRepository.signUpWithEmail(userProfile, password)
    }
}