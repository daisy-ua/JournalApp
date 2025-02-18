package com.daisy.journalapp.authentication.ui.credential

sealed interface GetCredentialResult {

    data class Success(val credential: UserCredentials) : GetCredentialResult

    data object Cancelled : GetCredentialResult

    data object Failure : GetCredentialResult

    data object NoCredentials : GetCredentialResult
}