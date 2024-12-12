package com.example.fundamentalpertama.Adapter.DarkMode

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class DarkModeAndLight {
    private val DarkColorScheme = darkColorScheme(
        primary = Color.White,
        secondary = Color(0xFF1F1F1F),
        background = Color(0xFF121212),
        surface = Color(0xFF121212),
        onPrimary = Color.White,
        onSecondary =  Color(0xFF282828),
        onBackground = Color.White,
        onSurface = Color(0xF0C0C0C0),
    )

    private val LightColorScheme = lightColorScheme(
        primary = Color.Black,
        secondary = Color(0xFFFFFFFF),
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.Black,
        onSecondary = Color(0xF0F8F8F8),
        onBackground = Color.Black,
        onSurface = Color(0xF0676767),
    )

    @Composable
    fun DarkModeTheme(
        darkTheme: Boolean= isSystemInDarkTheme(),
        content: @Composable () -> Unit
    ) {
        val colorScheme = if (darkTheme) {
            DarkColorScheme
        }else{
            LightColorScheme
        }

        MaterialTheme (
            colorScheme = colorScheme,
            typography = MaterialTheme.typography,
            shapes = MaterialTheme.shapes,
            content = content
        )
    }
}