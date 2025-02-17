package com.daisy.journalapp.core.presentation.utils

fun <T> Response<T, ErrorType>.handle(
    onSuccess: (T) -> Unit,
    onError: (ErrorType) -> Unit,
) {
    when (this) {
        is Response.Success -> onSuccess(data)
        is Response.Error -> onError(error)
    }
}