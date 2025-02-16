package com.daisy.journalapp.authentication.domain.usecase

import android.util.Patterns
import com.daisy.journalapp.authentication.domain.validation.EmailValidationResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmailValidatorUseCase @Inject constructor() {

    operator fun invoke(email: String): EmailValidationResult {
        return when {
            email.isEmpty() -> EmailValidationResult.EMPTY
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> EmailValidationResult.INVALID
            else -> EmailValidationResult.CORRECT
        }
    }
}