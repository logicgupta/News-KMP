package com.example.core_ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.core_ui.ui.theme.DarkColorScheme
import com.example.core_ui.ui.theme.LightColorScheme
import com.example.core_ui.ui.theme.Typography

@Composable
actual fun NewsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}