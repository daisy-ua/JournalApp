package com.daisy.journalapp.authentication.ui.auth

sealed interface AuthAction {

    data object OnLogInClick : AuthAction

    data object OnSignUpClick : AuthAction
}