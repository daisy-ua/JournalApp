package com.daisy.journalapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextDivider(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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
            text = text,
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
}