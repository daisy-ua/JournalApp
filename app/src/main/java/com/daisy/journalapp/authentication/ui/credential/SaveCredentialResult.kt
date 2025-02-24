package com.daisy.journalapp.authentication.ui.credential

sealed interface SaveCredentialResult {

    data object Success : SaveCredentialResult

    data object Cancelled : SaveCredentialResult

    data object Failure : SaveCredentialResult
}