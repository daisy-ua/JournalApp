package com.daisy.journalapp.authentication.ui.auth

import androidx.credentials.PasswordCredential
import com.daisy.journalapp.core.presentation.viewmodel.UiAction
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

sealed interface AuthAction : UiAction {

    data class OnLogInAuto(val credentials: PasswordCredential) : AuthAction

    data class OnLogInWithGoogle(val googleIdTokenCredential: GoogleIdTokenCredential) : AuthAction

    data object OnLogInClick : AuthAction

    data object OnSignUpClick : AuthAction

    data object OnAskForCredentials : AuthAction
}