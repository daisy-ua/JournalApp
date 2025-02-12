package com.daisy.journalapp.authentication.domain.validation

enum class PasswordValidationResult {
    CORRECT, EMPTY, SHORT, MISSING_SPECIAL_CHAR, MISSING_NUMERIC_CHAR
}