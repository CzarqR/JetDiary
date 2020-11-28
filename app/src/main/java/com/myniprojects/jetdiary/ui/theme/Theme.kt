package com.myniprojects.jetdiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = colorPrimaryDark,
    primaryVariant = colorPrimaryVariantDark,
    secondary = colorSecondaryDark,
    onPrimary = colorOnPrimaryDark,
    onSecondary = colorOnSecondaryDark
)

private val LightColorPalette = lightColors(
    primary = colorPrimaryLight,
    primaryVariant = colorPrimaryVariantLight,
    secondary = colorSecondaryLight,
    secondaryVariant = colorSecondaryVariantLight,
    onPrimary = colorOnPrimaryLight,
    onSecondary = colorOnSecondaryLight
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit)
{
    val colors = if (darkTheme)
    {
        DarkColorPalette
    }
    else
    {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}