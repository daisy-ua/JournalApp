package com.daisy.journalapp.core.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.daisy.journalapp.R
import com.daisy.journalapp.ui.theme.JournalAppTheme


@Composable
fun BlurredImageBackground(
    @DrawableRes imageModel: Int,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ColumnScope.() -> Unit
) {
    val imagePainter = rememberAsyncImagePainter(imageModel)
    val backgroundColor = MaterialTheme.colorScheme.background
    val topTintColor = if (isDarkTheme) backgroundColor.copy(alpha = .3f) else Color.Transparent

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = .99f }
                .drawWithContent {
                    val colors = listOf(
                        topTintColor,
                        backgroundColor
                    )
                    drawContent()
                    drawRect(
                        brush = Brush.verticalGradient(
                            colors = colors,
                            tileMode = TileMode.Clamp,
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        ),
                        blendMode = BlendMode.DstOut
                    )
                }
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GradientBackgroundPreview() {
    JournalAppTheme(darkTheme = true) {
        BlurredImageBackground(
            imageModel = R.drawable.auth_image,
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}