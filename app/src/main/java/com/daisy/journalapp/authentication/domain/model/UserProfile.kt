package com.daisy.journalapp.authentication.domain.model

data class UserProfile(
    val username: String?,

    val email: String?,

    val uid: String = "",
)
