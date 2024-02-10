package com.example.news_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform