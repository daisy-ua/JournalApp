package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.validation.UsernameValidationResult
import com.daisy.journalapp.core.presentation.AuthConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsernameValidatorUseCase @Inject constructor() {

    operator fun invoke(username: String): UsernameValidationResult {
        return when {
            username.isBlank() -> UsernameValidationResult.EMPTY
            username.length < AuthConfig.MIN_USERNAME_LENGTH -> UsernameValidationResult.SHORT
            else -> UsernameValidationResult.CORRECT
        }
    }
}