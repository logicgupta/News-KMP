package com.example.news_kmp.data.repository

import com.example.core.networking.data.APIResponse
import com.example.news_kmp.data.dto.NewsResponse
import kotlinx.coroutines.flow.Flow

internal interface HomeRepository {
    suspend fun fetchNews(): Flow<APIResponse<NewsResponse>>
}