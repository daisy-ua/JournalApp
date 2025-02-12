package com.daisy.journalapp.authentication.domain.model

data class UserProfile(
    val uid: String,

    val username: String?,

    val email: String?,
)
