package com.daisy.journalapp.core.presentation.utils

sealed interface Response<out D, out E : ErrorType> {

    data class Success<out D>(val data: D) : Response<D, Nothing>

    data class Error<out E : ErrorType>(val error: E) : Response<Nothing, E>
}