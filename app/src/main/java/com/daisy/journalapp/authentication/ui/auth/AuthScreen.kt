package com.daisy.journalapp.authentication.ui.auth

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.PasswordCredential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.daisy.journalapp.R
import com.daisy.journalapp.authentication.ui.credential.CredentialManagerWrapper
import com.daisy.journalapp.authentication.ui.credential.GetCredentialResult
import com.daisy.journalapp.core.presentation.ObserveEffects
import com.daisy.journalapp.core.presentation.UiText
import com.daisy.journalapp.core.presentation.components.BaseScaffold
import com.daisy.journalapp.core.presentation.components.JourneyActionButton
import com.daisy.journalapp.core.presentation.components.JourneyOutlinedActionButton
import com.daisy.journalapp.core.presentation.components.TextDivider
import com.daisy.journalapp.core.presentation.components.TransparentTopAppBar
import com.daisy.journalapp.core.presentation.utils.showToast
import com.daisy.journalapp.ui.theme.JournalAppTheme
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onLogInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val credentialManager = remember {
        CredentialManagerWrapper(context as ComponentActivity)
    }

    val scope = rememberCoroutineScope()

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (state.hasAskedForCredentials) return@LaunchedEffect

        viewModel.setAction(AuthAction.OnAskForCredentials)
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            when (val result = credentialManager.getStoredCredentials()) {
                is GetCredentialResult.Success -> {
                    viewModel.setAction(
                        AuthAction.OnLogInAuto(result.credential as PasswordCredential)
                    )
                }

                is GetCredentialResult.Failure ->
                    context.showToast(UiText.Resource(R.string.credentials_retrieval_error))

                else -> Unit
            }
        }
    }

    ObserveEffects(flow = viewModel.effect) { effect ->
        when (effect) {
            is AuthEffect.Error -> context.showToast(effect.error)
            AuthEffect.Success -> {
                context.showToast(UiText.Plain("Success"))
//                TODO: navigate to app
            }
        }
    }

    AuthScreenContent(
        onAction = { action ->
            when (action) {
                AuthAction.OnLogInClick -> onLogInClick()
                AuthAction.OnSignUpClick -> onSignUpClick()
                AuthAction.OnAskForCredentials -> {
                    scope.launch {
                        when (val result = credentialManager.signInWithGoogle()) {
                            is GetCredentialResult.Success -> viewModel.setAction(
                                AuthAction.OnLogInWithGoogle(
                                    result.credential as GoogleIdTokenCredential
                                )
                            )

                            is GetCredentialResult.Failure ->
                                context.showToast(UiText.Resource(R.string.credentials_retrieval_error))

                            else -> Unit
                        }
                    }
                }

                else -> Unit
            }
        }
    )
}

@Composable
private fun AuthScreenContent(
    onAction: (AuthAction) -> Unit,
) {
    BaseScaffold(
        topAppBar = {
            TransparentTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = stringResource(id = R.string.app_description),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.app_description_full),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(.7f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(36.dp))

            JourneyActionButton(
                text = stringResource(id = R.string.log_in),
                onClick = {
                    onAction(AuthAction.OnLogInClick)
                },
                isLoading = false,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            JourneyOutlinedActionButton(
                text = stringResource(id = R.string.sign_up),
                onClick = { onAction(AuthAction.OnSignUpClick) },
                isLoading = false,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextDivider(text = stringResource(id = R.string.or))

            Spacer(modifier = Modifier.height(16.dp))

            JourneyOutlinedActionButton(
                text = stringResource(id = R.string.continue_with_google),
                onClick = { onAction(AuthAction.OnAskForCredentials) },
                isLoading = false,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthScreenPreview() {
    JournalAppTheme {
        AuthScreenContent {}
    }
}