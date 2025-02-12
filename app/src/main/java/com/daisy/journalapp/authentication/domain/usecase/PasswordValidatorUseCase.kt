package com.daisy.journalapp.authentication.domain.usecase

import com.daisy.journalapp.authentication.domain.validation.PasswordValidationResult

class PasswordValidatorUseCase {

    operator fun invoke(password: String): PasswordValidationResult {
        return when {
            password.isBlank() -> PasswordValidationResult.EMPTY
            password.length < 8 -> PasswordValidationResult.SHORT
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