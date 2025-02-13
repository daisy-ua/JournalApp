package com.daisy.journalapp.authentication.ui.auth

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.daisy.journalapp.R
import com.daisy.journalapp.core.presentation.components.BaseScaffold
import com.daisy.journalapp.core.presentation.components.BlurredImageBackground
import com.daisy.journalapp.core.presentation.components.JourneyActionButton
import com.daisy.journalapp.core.presentation.components.JourneyOutlinedActionButton
import com.daisy.journalapp.core.presentation.components.TransparentTopAppBar
import com.daisy.journalapp.ui.theme.JournalAppTheme

@Composable
fun AuthScreen() {
    AuthScreenContent()
}

@Composable
fun AuthScreenContent() {
    BlurredImageBackground(
        imageModel = R.drawable.auth_image,
        modifier = Modifier.fillMaxSize()
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
                    isLoading = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    /*TODO*/
                }

                Spacer(modifier = Modifier.height(16.dp))

                JourneyOutlinedActionButton(
                    text = stringResource(id = R.string.sign_up),
                    isLoading = false,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                ) {
                    /*TODO*/
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    JournalAppTheme {
        AuthScreenContent()
    }
}