package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.validation.UsernameValidationResult

class UsernameValidatorUseCase {

    operator fun invoke(username: String): UsernameValidationResult {
        return when {
            username.isBlank() -> UsernameValidationResult.EMPTY
            username.length < 3 -> UsernameValidationResult.SHORT
            else -> UsernameValidationResult.CORRECT
        }
    }
}