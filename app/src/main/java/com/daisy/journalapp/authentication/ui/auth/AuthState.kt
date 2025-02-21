package com.daisy.journalapp.authentication.ui.auth

import com.daisy.journalapp.core.presentation.viewmodel.UiState

data class AuthState(

    val hasAskedForCredentials: Boolean = false
) : UiState
