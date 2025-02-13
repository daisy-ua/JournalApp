package com.daisy.journalapp.authentication.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.journalapp.R
import com.daisy.journalapp.core.presentation.components.BaseScaffold
import com.daisy.journalapp.core.presentation.components.BlurredImageBackground
import com.daisy.journalapp.core.presentation.components.EmailIcon
import com.daisy.journalapp.core.presentation.components.JourneyActionButton
import com.daisy.journalapp.core.presentation.components.JourneyOutlinedActionButton
import com.daisy.journalapp.core.presentation.components.JourneyPasswordTextField
import com.daisy.journalapp.core.presentation.components.JourneyTextField
import com.daisy.journalapp.core.presentation.components.PersonIcon
import com.daisy.journalapp.core.presentation.components.TransparentTopAppBar
import com.daisy.journalapp.ui.theme.JournalAppTheme

@Composable
fun RegisterScreen(
    onLogInClick: () -> Unit,
    onUpClick: () -> Unit,
) {
    RegisterScreenContent(
        onLogInClick = onLogInClick,
        onUpClick = onUpClick
    )
}

@Composable
private fun RegisterScreenContent(
    onLogInClick: () -> Unit = {},
    onUpClick: () -> Unit = {},
) {
    BlurredImageBackground(
        imageModel = R.drawable.auth_image,
        modifier = Modifier.fillMaxSize()
    ) {
        BaseScaffold(
            topAppBar = {
                TransparentTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onUpClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
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
                    text = stringResource(id = R.string.register_welcome_text),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(150.dp))

                JourneyTextField(
                    state = rememberTextFieldState(),
                    startIcon = PersonIcon,
                    endIcon = null,
                    keyboardType = KeyboardType.Unspecified,
                    hint = stringResource(id = R.string.name),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                JourneyTextField(
                    state = rememberTextFieldState(),
                    startIcon = EmailIcon,
                    endIcon = null,
                    keyboardType = KeyboardType.Email,
                    hint = stringResource(id = R.string.email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                JourneyPasswordTextField(
                    state = rememberTextFieldState(),
                    isPasswordVisible = false,
                    onTogglePasswordVisibility = {},
                    hint = stringResource(id = R.string.password),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(48.dp))

                JourneyActionButton(
                    text = stringResource(id = R.string.sign_up),
                    isLoading = false,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    /*TODO*/
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .weight(5f)
                            .height(1.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(.2f),
                                shape = RoundedCornerShape(20.dp)
                            )
                    )

                    Text(
                        text = stringResource(id = R.string.or),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(
                        modifier = Modifier
                            .weight(5f)
                            .height(1.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(.2f),
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                JourneyOutlinedActionButton(
                    text = stringResource(id = R.string.log_in),
                    onClick = onLogInClick,
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
private fun RegisterScreenPreview() {
    JournalAppTheme(darkTheme = true) {
        RegisterScreenContent()
    }
}