package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.validation.PasswordValidationResult
import com.daisy.journalapp.core.presentation.AuthConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordValidatorUseCase @Inject constructor() {

    operator fun invoke(password: String): PasswordValidationResult {
        return when {
            password.isBlank() -> PasswordValidationResult.EMPTY
            password.length < AuthConfig.MIN_PASSWORD_LENGTH -> PasswordValidationResult.SHORT
            !validatePasswordHasDigit(password) -> PasswordValidationResult.MISSING_NUMERIC_CHAR
            !validatePasswordHasSpecialChar(password) -> PasswordValidationResult.MISSING_SPECIAL_CHAR
            else -> PasswordValidationResult.CORRECT
        }
    }

    private fun validatePasswordHasDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    private fun validatePasswordHasSpecialChar(password: String): Boolean {
        val specialChars = "@\$!%*?&"
        return password.any { it in specialChars }
    }
}