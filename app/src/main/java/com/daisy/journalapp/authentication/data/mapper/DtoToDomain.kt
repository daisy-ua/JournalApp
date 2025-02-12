package com.daisy.journalapp.authentication.data.mapper

import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserProfile() = UserProfile(
    uid = uid,
    username = displayName,
    email = email,
)