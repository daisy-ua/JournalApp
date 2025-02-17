package com.daisy.journalapp.authentication.ui.login

import androidx.lifecycle.viewModelScope
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.domain.usecase.EmailValidatorUseCase
import com.daisy.journalapp.authentication.domain.usecase.SignInWithEmailUseCase
import com.daisy.journalapp.authentication.domain.validation.EmailValidationResult
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.utils.asTrimmedString
import com.daisy.journalapp.core.presentation.utils.handle
import com.daisy.journalapp.core.presentation.utils.message
import com.daisy.journalapp.core.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
) : BaseViewModel<LoginState, LoginAction, LoginEffect>(LoginState()) {

    override fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            if (!validateUserInput()) {
                return@launch
            }

            updateState { copy(isLoginInProgress = true, emailError = null, passwordError = null) }

            val result = signInWithEmailUseCase(
                email = currentState.email.asTrimmedString(),
                password = currentState.password.text.toString()
            )

            updateState { copy(isLoginInProgress = false) }

            result.handle(
                onSuccess = { setEffect { LoginEffect.Success } },
                onError = { error -> LoginEffect.Error(error.message) }
            )
        }
    }

    private fun validateUserInput(): Boolean {
        val isEmailInvalid = emailValidatorUseCase(
            email = currentState.email.text.trim().toString()
        ) != EmailValidationResult.CORRECT

        val isPasswordInvalid = currentState.password.text.isBlank()

        if (isEmailInvalid || isPasswordInvalid) {
            updateState {
                copy(
                    emailError = if (isEmailInvalid) UiText.Resource(R.string.invalid_email) else null,
                    passwordError = if (isPasswordInvalid) UiText.Resource(R.string.password_required) else null
                )
            }

            return false
        }

        return true
    }

    private fun togglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }
}