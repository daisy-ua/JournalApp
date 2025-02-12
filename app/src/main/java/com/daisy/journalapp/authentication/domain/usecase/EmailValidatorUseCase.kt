package com.daisy.journalapp.authentication.domain.usecase

import android.util.Patterns
import com.daisy.journalapp.authentication.domain.validation.EmailValidationResult

class EmailValidatorUseCase {

    operator fun invoke(email: String): EmailValidationResult {
        return when {
            email.isEmpty() -> EmailValidationResult.EMPTY
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailValidationResult.INVALID
            else -> EmailValidationResult.CORRECT
        }
    }
}