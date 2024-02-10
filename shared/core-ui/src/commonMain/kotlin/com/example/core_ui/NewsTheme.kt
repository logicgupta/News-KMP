package com.example.core_ui

import androidx.compose.runtime.Composable

@Composable
expect fun NewsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
)