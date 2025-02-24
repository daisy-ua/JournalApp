package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.repository.AuthRepository
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(googleIdTokenCredential: GoogleIdTokenCredential): AuthResponse {
        return authRepository.signInWithGoogle(googleIdTokenCredential)
    }
}