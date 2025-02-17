package com.daisy.journalapp.authentication.ui.login

import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmodel.UiEffect

interface LoginEffect : UiEffect {

    data object Success : LoginEffect

    data class Error(val error: UiText) : LoginEffect
}