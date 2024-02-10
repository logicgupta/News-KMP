package com.example.news_kmp

import androidx.compose.ui.window.ComposeUIViewController
import com.example.news_kmp.app.NewsApp
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark

    NewsApp(darkTheme = isDarkTheme, dynamicColor = false)
}