package com.daisy.journalapp.authentication.ui.login

import com.daisy.journalapp.core.presentation.viewmode.UiAction

sealed interface LoginAction: UiAction {

    data object OnTogglePasswordVisibility : LoginAction

    data object OnLoginClick : LoginAction

    data object OnRegisterClick : LoginAction

    data object OnNavigateUp: LoginAction
}