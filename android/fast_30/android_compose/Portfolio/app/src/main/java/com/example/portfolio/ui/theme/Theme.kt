package com.example.portfolio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = darkPrimaryBlue,
    primaryVariant = primaryBlue,
    secondary = darkSecondaryBlue,
    onSecondary = secondaryBlue,
    surface = backgroundColor
)

private val LightColorPalette = lightColors(
    primary = lightPrimaryBlue,
    primaryVariant = primaryBlue,
    secondary = secondaryBlue,
    onSecondary = lightSecondaryBlue,
    surface = backgroundColor

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PortfolioTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}