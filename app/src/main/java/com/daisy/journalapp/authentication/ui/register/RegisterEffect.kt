package com.daisy.journalapp.authentication.ui.register

import com.daisy.journalapp.authentication.ui.credential.UserCredentials
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmodel.UiEffect

sealed interface RegisterEffect : UiEffect {

    data class Success(val credentials: UserCredentials) : RegisterEffect

    data class Error(val error: UiText) : RegisterEffect
}