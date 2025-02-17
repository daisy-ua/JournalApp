package com.daisy.journalapp.core.presentation.utils

import com.daisy.journalapp.R
import com.daisy.journalapp.core.presentation.UiText

val ErrorType.message: UiText
    get() = when (this) {
        is AuthError -> this.message
        is NetworkError -> this.message
        else -> UiText.Resource(R.string.unknown_error)
    }


val AuthError.message: UiText
    get() = when (this) {
        is AuthError.InvalidCredentials -> UiText.Resource(R.string.invalid_credentials_error)
        is AuthError.UserAlreadyExists -> UiText.Resource(R.string.user_already_exists_error)
        is AuthError.UserNotFound -> UiText.Resource(R.string.user_not_found_error)
        is AuthError.UnknownAuthError -> UiText.Resource(R.string.unknown_auth_error)
    }


val NetworkError.message: UiText
    get() = when (this) {
        NetworkError.NoInternetConnection -> UiText.Resource(R.string.no_internet_error)
        NetworkError.TimeoutError -> UiText.Resource(R.string.timeout_error)
        NetworkError.UnknownNetworkError -> UiText.Resource(R.string.unknown_network_error)
    }
