package com.daisy.journalapp.authentication.ui.auth

import androidx.lifecycle.viewModelScope
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.domain.usecase.SignInWithEmailUseCase
import com.daisy.journalapp.authentication.ui.credential.UserCredentials
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.utils.handle
import com.daisy.journalapp.core.presentation.utils.message
import com.daisy.journalapp.core.presentation.viewmodel.BaseViewModel
import com.daisy.journalapp.core.presentation.viewmodel.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
) : BaseViewModel<UiState, AuthAction, AuthEffect>(object : UiState {}) {

    override fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnLogInAuto -> login(action.credentials)
            else -> Unit
        }
    }

    private fun login(credentials: UserCredentials) = viewModelScope.launch {
        try {
            val result = signInWithEmailUseCase(
                email = credentials.id,
                password = credentials.password!!
            )

            result.handle(
                onSuccess = { setEffect { AuthEffect.Success } },
                onError = { error -> setEffect { AuthEffect.Error(error.message) } }
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
            setEffect { AuthEffect.Error(UiText.Resource(R.string.credentials_retrieval_error)) }
        }
    }
}