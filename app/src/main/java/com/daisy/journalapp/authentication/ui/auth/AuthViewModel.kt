package com.daisy.journalapp.authentication.ui.auth

import androidx.credentials.Credential
import androidx.credentials.PasswordCredential
import androidx.lifecycle.viewModelScope
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.domain.repository.AuthResponse
import com.daisy.journalapp.authentication.domain.usecase.SignInWithEmailUseCase
import com.daisy.journalapp.authentication.domain.usecase.SignInWithGoogleUseCase
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.utils.handle
import com.daisy.journalapp.core.presentation.utils.message
import com.daisy.journalapp.core.presentation.viewmodel.BaseViewModel
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
) : BaseViewModel<AuthState, AuthAction, AuthEffect>(AuthState()) {

    override fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnLogInAuto -> signInWithEmail(action.credentials)
            is AuthAction.OnAskForCredentials -> markCredentialPromptShown()
            is AuthAction.OnLogInWithGoogle -> signInWithGoogle(action.googleIdTokenCredential)
            else -> Unit
        }
    }

    private fun signInWithEmail(credentials: PasswordCredential) {
        signIn {
            signInWithEmailUseCase(
                email = credentials.id,
                password = credentials.password
            )
        }
    }

    private fun signInWithGoogle(idTokenCredential: GoogleIdTokenCredential) {
        signIn {
            signInWithGoogleUseCase(idTokenCredential)
        }
    }

    private fun signIn(signInMethod: suspend () -> AuthResponse) = viewModelScope.launch {
        try {
            val result = signInMethod()

            result.handle(
                onSuccess = { setEffect { AuthEffect.Success } },
                onError = { error -> setEffect { AuthEffect.Error(error.message) } }
            )
        } catch (e: NullPointerException) {
            e.printStackTrace()
            setEffect { AuthEffect.Error(UiText.Resource(R.string.credentials_retrieval_error)) }
        }
    }

    private fun markCredentialPromptShown() {
        updateState { copy(hasAskedForCredentials = true) }
    }
}