package com.example.news_kmp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: NewsSubResponse
)

@Serializable
data class NewsSubResponse(
    val results: List<NewsResults>
)

@Serializable
data class NewsResults(
    val lang: String, val title: String, val body: String, val image: String?
)



