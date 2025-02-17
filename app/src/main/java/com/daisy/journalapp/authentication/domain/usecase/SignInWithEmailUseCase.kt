package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(email: String, password: String): AuthResponse {
        return authRepository.signInWithEmail(email, password)
    }
}