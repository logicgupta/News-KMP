package com.example.news_kmp

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(sharedModule())
    }
}