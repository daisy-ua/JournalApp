package com.daisy.journalapp.authentication.ui.credential

import androidx.credentials.Credential

sealed interface GetCredentialResult {

    data class Success(val credential: Credential) : GetCredentialResult

    data object Cancelled : GetCredentialResult

    data object Failure : GetCredentialResult

    data object NoCredentials : GetCredentialResult
}