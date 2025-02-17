package com.daisy.journalapp.authentication.domain.repository

import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.daisy.journalapp.core.presentation.utils.ErrorType
import com.daisy.journalapp.core.presentation.utils.Response

typealias AuthResponse = Response<UserProfile, ErrorType>