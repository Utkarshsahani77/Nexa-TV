package com.nexatv.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF6C5CE7),
    secondary = Color(0xFF00CEC9),
    background = Color(0xFF0D0D0D),
    surface = Color(0xFF1A1A1A)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF6C5CE7),
    secondary = Color(0xFF00CEC9),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5)
)

@Composable
fun NexaTVTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
