package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = ShajeenSkyBlue,
    onPrimary = Color.White,
    primaryContainer = ShajeenDarkCard,
    onPrimaryContainer = ShajeenGoldLight,
    secondary = ShajeenGold,
    onSecondary = ShajeenDarkBlue,
    background = ShajeenDarkBackground,
    onBackground = ShajeenDarkTextPrimary,
    surface = ShajeenDarkSurface,
    onSurface = ShajeenDarkTextPrimary,
    surfaceVariant = ShajeenDarkCard,
    onSurfaceVariant = ShajeenDarkTextSecondary,
    outline = Color(0xFF2A4D73)
)

private val LightColorScheme = lightColorScheme(
    primary = ShajeenSkyBlue, // #0288D1 Sky Blue
    onPrimary = Color.White,
    primaryContainer = ShajeenSkyContainer, // #E0F2FE
    onPrimaryContainer = ShajeenDarkBlue, // #0C2340
    secondary = ShajeenDarkBlue, // #0C2340 Navy
    onSecondary = Color.White,
    secondaryContainer = ShajeenSkyContainer,
    onSecondaryContainer = ShajeenDarkBlue,
    tertiary = ShajeenGold,
    onTertiary = Color.White,
    background = ShajeenBackgroundLight, // #F1F7FD
    onBackground = ShajeenBodyText, // #1E293B
    surface = ShajeenSurfaceLight, // #FFFFFF
    onSurface = ShajeenBodyText, // #1E293B
    surfaceVariant = Color(0xFFF8FAFC),
    onSurfaceVariant = ShajeenSecondaryText, // #64748B
    outline = ShajeenCardBorder // #E2E8F0
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve brand identity by default
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
