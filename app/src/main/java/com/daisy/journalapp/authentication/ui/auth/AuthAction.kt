package com.daisy.journalapp.authentication.ui.auth

import com.daisy.journalapp.core.presentation.viewmodel.UiAction

sealed interface AuthAction : UiAction {

    data object OnLogInClick : AuthAction

    data object OnSignUpClick : AuthAction
}