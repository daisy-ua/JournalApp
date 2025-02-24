package com.daisy.journalapp.authentication.ui.register

import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.viewmodel.UiEffect

sealed interface RegisterEffect : UiEffect {

    data class Success(val id: String, val password: String) : RegisterEffect

    data class Error(val error: UiText) : RegisterEffect
}