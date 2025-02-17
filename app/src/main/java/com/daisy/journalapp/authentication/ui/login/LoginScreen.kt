@file:OptIn(ExperimentalMaterial3Api::class)

package com.daisy.journalapp.authentication.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daisy.journalapp.R
import com.daisy.journalapp.core.presentation.ObserveEffects
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.components.ArrowLeftIcon
import com.daisy.journalapp.core.presentation.components.BaseScaffold
import com.daisy.journalapp.core.presentation.components.BlurredImageBackground
import com.daisy.journalapp.core.presentation.components.EmailIcon
import com.daisy.journalapp.core.presentation.components.JourneyActionButton
import com.daisy.journalapp.core.presentation.components.JourneyOutlinedActionButton
import com.daisy.journalapp.core.presentation.components.JourneyPasswordTextField
import com.daisy.journalapp.core.presentation.components.JourneyTextField
import com.daisy.journalapp.core.presentation.components.TextDivider
import com.daisy.journalapp.core.presentation.components.TransparentTopAppBar
import com.daisy.journalapp.core.presentation.utils.showToast
import com.daisy.journalapp.ui.theme.JournalAppTheme

@Composable
fun LoginScreen(
    onSignUpClick: () -> Unit,
    onUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveEffects(flow = viewModel.effect) { effect ->
        when (effect) {
            is LoginEffect.Error -> context.showToast(effect.error)
            LoginEffect.Success -> {
                context.showToast(UiText.Plain("Success"))
//                TODO: navigate to app
            }
        }
    }

    LoginScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> onSignUpClick()
                LoginAction.OnNavigateUp -> onUpClick()
                else -> Unit
            }

            viewModel.setAction(action)
        }
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    val context = LocalContext.current

    BlurredImageBackground(
        imageModel = R.drawable.auth_image,
        modifier = Modifier.fillMaxSize()
    ) {
        BaseScaffold(
            topAppBar = {
                TransparentTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = { onAction(LoginAction.OnNavigateUp) }
                        ) {
                            Icon(
                                imageVector = ArrowLeftIcon,
                                contentDescription = stringResource(id = R.string.navigate_up_description),
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    },
                )
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = stringResource(id = R.string.login_welcome_text),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(150.dp))

                JourneyTextField(
                    state = state.email,
                    startIcon = EmailIcon,
                    endIcon = null,
                    error = state.emailError?.asString(context),
                    keyboardType = KeyboardType.Email,
                    hint = stringResource(id = R.string.email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                JourneyPasswordTextField(
                    state = state.password,
                    isPasswordVisible = state.isPasswordVisible,
                    onTogglePasswordVisibility = { onAction(LoginAction.OnTogglePasswordVisibility) },
                    hint = stringResource(id = R.string.password),
                    error = state.passwordError?.asString(context),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(48.dp))

                JourneyActionButton(
                    text = stringResource(id = R.string.log_in),
                    isLoading = state.isLoginInProgress,
                    onClick = { onAction(LoginAction.OnLoginClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextDivider(text = stringResource(id = R.string.or))

                Spacer(modifier = Modifier.height(16.dp))

                JourneyOutlinedActionButton(
                    text = stringResource(id = R.string.sign_up),
                    onClick = { onAction(LoginAction.OnRegisterClick) },
                    isLoading = false,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    JournalAppTheme(darkTheme = true) {
        LoginScreenContent(LoginState()) {}
    }
}