package com.daisy.journalapp.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topAppBar,
        contentColor = MaterialTheme.colorScheme.onBackground,
        containerColor = Color.Transparent,
        modifier = modifier,
    ) { paddingValues ->
        content(paddingValues)
    }
}
