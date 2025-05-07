package com.example.sommeliervinhos.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    background = BackgroundLight,
    onBackground = OnBackgroundLight
)

@Composable
fun SommelierTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography(),
        content = content
    )
}
