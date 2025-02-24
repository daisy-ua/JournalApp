package com.daisy.journalapp.authentication.ui.register

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.ui.credential.CredentialManagerWrapper
import com.daisy.journalapp.core.presentation.ObserveEffects
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.components.ArrowLeftIcon
import com.daisy.journalapp.core.presentation.components.BaseScaffold
import com.daisy.journalapp.core.presentation.components.EmailIcon
import com.daisy.journalapp.core.presentation.components.JourneyActionButton
import com.daisy.journalapp.core.presentation.components.JourneyOutlinedActionButton
import com.daisy.journalapp.core.presentation.components.JourneyPasswordTextField
import com.daisy.journalapp.core.presentation.components.JourneyTextField
import com.daisy.journalapp.core.presentation.components.PersonIcon
import com.daisy.journalapp.core.presentation.components.TextDivider
import com.daisy.journalapp.core.presentation.components.TransparentTopAppBar
import com.daisy.journalapp.core.presentation.utils.showToast
import com.daisy.journalapp.ui.theme.JournalAppTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onLogInClick: () -> Unit,
    onUpClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val credentialManager = remember {
        CredentialManagerWrapper(context as ComponentActivity)
    }

    ObserveEffects(flow = viewModel.effect) { effect ->
        when (effect) {
            is RegisterEffect.Error -> {
                context.showToast(effect.error)
            }

            is RegisterEffect.Success -> {
                context.showToast(UiText.Plain("Success"))
                scope.launch {
                    credentialManager.saveCredentials(effect.id, effect.password)
                }
//                TODO: navigate to app
            }
        }
    }

    RegisterScreenContent(
        state = state,
        onAction = { action ->
            when (action) {
                RegisterAction.OnLoginClick -> onLogInClick()
                RegisterAction.OnNavigateUp -> onUpClick()
                else -> Unit
            }
            viewModel.setAction(action)
        }
    )
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    BaseScaffold(
        topAppBar = {
            TransparentTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onAction(RegisterAction.OnNavigateUp) }) {
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
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.register_welcome_text),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(50.dp))

            JourneyTextField(
                state = state.username,
                startIcon = PersonIcon,
                error = state.usernameError?.asString(context),
                endIcon = null,
                keyboardType = KeyboardType.Unspecified,
                hint = stringResource(id = R.string.name),
                onKeyboardAction = { focusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            JourneyTextField(
                state = state.email,
                startIcon = EmailIcon,
                error = state.emailError?.asString(context),
                endIcon = null,
                keyboardType = KeyboardType.Email,
                hint = stringResource(id = R.string.email),
                onKeyboardAction = { focusManager.moveFocus(FocusDirection.Down) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            JourneyPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                error = state.passwordError?.asString(context),
                onTogglePasswordVisibility = { onAction(RegisterAction.OnTogglePasswordVisibility) },
                hint = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(48.dp))

            JourneyActionButton(
                text = stringResource(id = R.string.sign_up),
                onClick = { onAction(RegisterAction.OnRegisterClick) },
                isLoading = state.isRegisterInProgress,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextDivider(text = stringResource(id = R.string.or))

            Spacer(modifier = Modifier.height(16.dp))

            JourneyOutlinedActionButton(
                text = stringResource(id = R.string.log_in),
                onClick = { onAction(RegisterAction.OnLoginClick) },
                isLoading = false,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    JournalAppTheme(darkTheme = true) {
        RegisterScreenContent(RegisterState()) {}
    }
}