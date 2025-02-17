package com.daisy.journalapp.core.presentation.utils

import androidx.compose.foundation.text.input.TextFieldState

fun TextFieldState.asTrimmedString(): String {
    return text.trim().toString()
}