package com.daisy.journalapp.authentication.ui.credential

sealed interface SaveCredentialResult {

    data class Success(val credential: UserCredentials) : SaveCredentialResult

    data object Cancelled : SaveCredentialResult

    data object Failure : SaveCredentialResult
}