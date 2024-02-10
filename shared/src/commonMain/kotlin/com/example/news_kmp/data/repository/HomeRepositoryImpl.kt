package com.example.news_kmp.data.repository

import com.example.core.networking.data.APIResponse
import com.example.news_kmp.data.dto.NewsResponse
import com.example.news_kmp.data.dto.NewsSubResponse
import com.example.news_kmp.data.network.NewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(private val newsDataSource: NewsDataSource) : HomeRepository {

    override suspend fun fetchNews(): Flow<APIResponse<NewsResponse>> {
        val result = newsDataSource.fetchNews()
        return flow<APIResponse<NewsResponse>> {
            if (result.status == APIResponse.Status.SUCCESS) {
                emit(
                    APIResponse.success(
                        result.data ?: NewsResponse(NewsSubResponse(emptyList()))
                    )
                )
            } else {
                emit(
                    APIResponse.error(errorMessage = result.errorMessage.orEmpty())
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}