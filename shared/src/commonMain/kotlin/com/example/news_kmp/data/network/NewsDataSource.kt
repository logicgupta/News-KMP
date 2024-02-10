package com.example.news_kmp.data.network

import com.example.core.CoreBuildKonfig
import com.example.core.networking.data.BaseDataSource
import com.example.news_kmp.data.dto.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class NewsDataSource(private val client: HttpClient) : BaseDataSource() {

    suspend fun fetchNews() = getResult<NewsResponse> {
        client.get(
            "${CoreBuildKonfig.BASE_URL}/article/getArticles?query=%7B%22%24query%22%3A%7B%22locationUri%22%3A%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FIndia%22%7D%2C%22%24filter%22%3A%7B%22forceMaxDataTimeWindow%22%3A%2231%22%7D%7D&resultType=articles&articlesSortBy=date&apiKey=${CoreBuildKonfig.API_KEY}"
        )

    }
}
