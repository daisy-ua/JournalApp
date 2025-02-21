package com.daisy.journalapp.authentication.ui.auth

import com.daisy.journalapp.authentication.ui.credential.UserCredentials
import com.daisy.journalapp.core.presentation.viewmodel.UiAction

sealed interface AuthAction : UiAction {

    data class OnLogInAuto(val credentials: UserCredentials) : AuthAction

    data object OnLogInClick : AuthAction

    data object OnSignUpClick : AuthAction

    data object OnAskForCredentials : AuthAction
}