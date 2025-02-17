package com.daisy.journalapp.authentication.ui.register

import androidx.lifecycle.viewModelScope
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.domain.model.UserProfile
import com.daisy.journalapp.authentication.domain.usecase.EmailValidatorUseCase
import com.daisy.journalapp.authentication.domain.usecase.PasswordValidatorUseCase
import com.daisy.journalapp.authentication.domain.usecase.SignUpWithEmailUseCase
import com.daisy.journalapp.authentication.domain.usecase.UsernameValidatorUseCase
import com.daisy.journalapp.authentication.domain.validation.EmailValidationResult
import com.daisy.journalapp.authentication.domain.validation.PasswordValidationResult
import com.daisy.journalapp.authentication.domain.validation.UsernameValidationResult
import com.daisy.journalapp.core.presentation.AuthConfig
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.utils.asTrimmedString
import com.daisy.journalapp.core.presentation.utils.handle
import com.daisy.journalapp.core.presentation.utils.message
import com.daisy.journalapp.core.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val usernameValidatorUseCase: UsernameValidatorUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
) : BaseViewModel<RegisterState, RegisterAction, RegisterEffect>(RegisterState()) {

    override fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            if (!validateUserInput()) {
                return@launch
            }

            updateState {
                copy(
                    isRegisterInProgress = true,
                    usernameError = null,
                    emailError = null,
                    passwordError = null
                )
            }

            val userProfile = UserProfile(
                username = currentState.username.asTrimmedString(),
                email = currentState.email.asTrimmedString()
            )

            val result = signUpWithEmailUseCase(
                userProfile = userProfile,
                password = currentState.password.text.toString()
            )

            updateState { copy(isRegisterInProgress = false) }

            result.handle(
                onSuccess = { setEffect { RegisterEffect.Success } },
                onError = { error -> RegisterEffect.Error(error.message) }
            )
        }
    }

    private fun validateUserInput(): Boolean {
        val usernameValidationResult = usernameValidatorUseCase(
            username = currentState.username.asTrimmedString()
        )
        val emailValidationResult = emailValidatorUseCase(
            email = currentState.email.asTrimmedString()
        )
        val passwordValidationResult = passwordValidatorUseCase(
            password = currentState.password.text.toString()
        )

        if (usernameValidationResult == UsernameValidationResult.CORRECT &&
            emailValidationResult == EmailValidationResult.CORRECT &&
            passwordValidationResult == PasswordValidationResult.CORRECT
        ) {
            return true
        }

        val usernameError = processUsernameError(usernameValidationResult)
        val emailError = processEmailError(emailValidationResult)
        val passwordError = processPasswordError(passwordValidationResult)

        updateState {
            copy(
                usernameError = usernameError,
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return false
    }

    private fun togglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    private fun processUsernameError(usernameValidationResult: UsernameValidationResult): UiText? {
        return when (usernameValidationResult) {
            UsernameValidationResult.EMPTY -> UiText.Resource(R.string.username_required)
            UsernameValidationResult.SHORT -> UiText.Resource(
                R.string.username_too_short,
                listOf(AuthConfig.MIN_USERNAME_LENGTH)
            )

            UsernameValidationResult.CORRECT -> null
        }
    }

    private fun processEmailError(emailValidationResult: EmailValidationResult): UiText? {
        return when (emailValidationResult) {
            EmailValidationResult.EMPTY -> UiText.Resource(R.string.email_required)
            EmailValidationResult.INVALID -> UiText.Resource(R.string.email_required)
            EmailValidationResult.CORRECT -> null
        }
    }

    private fun processPasswordError(passwordValidationResult: PasswordValidationResult): UiText? {
        return when (passwordValidationResult) {
            PasswordValidationResult.EMPTY -> UiText.Resource(R.string.password_required)
            PasswordValidationResult.SHORT -> UiText.Resource(
                R.string.password_too_short,
                listOf(AuthConfig.MIN_PASSWORD_LENGTH)
            )

            PasswordValidationResult.MISSING_SPECIAL_CHAR -> UiText.Resource(R.string.password_missing_special)
            PasswordValidationResult.MISSING_NUMERIC_CHAR -> UiText.Resource(R.string.password_missing_numeric)
            PasswordValidationResult.CORRECT -> null
        }
    }
}
