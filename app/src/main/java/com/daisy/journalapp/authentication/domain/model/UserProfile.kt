package com.daisy.journalapp.authentication.domain.model

data class UserProfile(
    val username: String?,

    val email: String?,

    val password: String? = null,

    val uid: String = "",
)
