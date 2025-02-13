package com.daisy.journalapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.daisy.journalapp.R

val NunitoFont = FontFamily(
    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium)
)

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = NunitoFont),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = NunitoFont),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = NunitoFont),

    headlineLarge = defaultTypography.headlineLarge.copy(
        fontFamily = NunitoFont,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = NunitoFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = NunitoFont),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = NunitoFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = NunitoFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = NunitoFont),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = NunitoFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = NunitoFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = NunitoFont),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = NunitoFont),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = NunitoFont),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = NunitoFont)
)