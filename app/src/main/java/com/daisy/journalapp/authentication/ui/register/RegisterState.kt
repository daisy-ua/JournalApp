package com.daisy.journalapp.authentication.ui.register

import androidx.compose.foundation.text.input.TextFieldState
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmodel.UiState

data class RegisterState(

    val username: TextFieldState = TextFieldState(),

    val email: TextFieldState = TextFieldState(),

    val password: TextFieldState = TextFieldState(),

    val isPasswordVisible: Boolean = false,

    val isRegisterInProgress: Boolean = false,

    val usernameError: UiText? = null,

    val emailError: UiText? = null,

    val passwordError: UiText? = null,
) : UiState
