package com.daisy.journalapp.core.presentation.utils

interface ErrorType

sealed interface AuthError : ErrorType {

    data object InvalidCredentials : AuthError

    data object UserAlreadyExists : AuthError

    data object UserNotFound : AuthError

    data object UnknownAuthError : AuthError
}

sealed interface NetworkError : ErrorType {

    data object NoInternetConnection : NetworkError

    data object TimeoutError : NetworkError

    data object UnknownNetworkError : NetworkError
}