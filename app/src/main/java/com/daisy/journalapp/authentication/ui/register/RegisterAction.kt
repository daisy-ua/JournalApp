package com.daisy.journalapp.authentication.ui.register

import com.daisy.journalapp.core.presentation.viewmodel.UiAction

sealed interface RegisterAction : UiAction {

    data object OnTogglePasswordVisibility : RegisterAction

    data object OnLoginClick : RegisterAction

    data object OnRegisterClick : RegisterAction

    data object OnNavigateUp : RegisterAction
}