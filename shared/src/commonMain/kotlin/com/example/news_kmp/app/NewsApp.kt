package com.example.news_kmp.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.NewsTheme
import com.example.news_kmp.screen.HomeScreen
import com.example.news_kmp.viewmodel.NewsViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun NewsApp(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {

    NewsTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val viewModel = getViewModel("home", viewModelFactory {
                NewsViewModel()
            })

            HomeScreen(viewModel)

        }

    }
}