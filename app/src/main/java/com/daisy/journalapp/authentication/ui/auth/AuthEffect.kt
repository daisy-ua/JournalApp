package com.daisy.journalapp.authentication.ui.auth

import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmodel.UiEffect

interface AuthEffect : UiEffect {

    data object Success : AuthEffect

    data class Error(val error: UiText) : AuthEffect
}