package com.daisy.journalapp.authentication.ui.login

import androidx.compose.foundation.text.input.TextFieldState
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmode.UiState

data class LoginState(

    val email: TextFieldState = TextFieldState(),

    val password: TextFieldState = TextFieldState(),

    val isPasswordVisible: Boolean = false,

    val isLoginInProgress: Boolean = false,

    val emailError: UiText? = null,

    val passwordError: UiText? = null,
) : UiState
