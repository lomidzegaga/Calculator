package com.example.calculator.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val darkColorScheme = darkColorScheme(
    primary = buttonLowEmphasisDark,
    secondary = buttonMediumEmphasisDark,
    tertiary = buttonHighEmphasisDark,
    surface = white,
    onSurface = black,
    onBackground = white,
    background = darkBackground
)

private val lightColorScheme = lightColorScheme(
    primary = buttonLowEmphasisLight,
    secondary = buttonMediumEmphasisLight,
    tertiary = buttonHighEmphasisLight,
    surface = black,
    onSurface = white,
    onBackground = white,
    background = lightBackground
)

@Composable
fun CalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorScheme else lightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}