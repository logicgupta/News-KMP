package com.example.news_kmp.data.repository

import com.example.core.networking.data.APIErrorResponse
import com.example.core.networking.data.APIResponse
import com.example.news_kmp.CACHE_EXPIRE_TIME
import com.example.news_kmp.data.cache.PreferenceUtilImpl
import com.example.news_kmp.data.db.NewsLocalDataSource
import com.example.news_kmp.data.dto.NewsResponse
import com.example.news_kmp.data.dto.NewsResults
import com.example.news_kmp.data.dto.NewsSubResponse
import com.example.news_kmp.data.network.NewsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get

class HomeRepositoryImpl(
    private val newsDataSource: NewsDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val preferenceUtilImpl: PreferenceUtilImpl
) : HomeRepository {

    private val store =
        StoreBuilder.from<String, APIResponse<NewsResponse>, APIResponse<NewsResponse>>(fetcher = Fetcher.of { _ ->
            val result = newsDataSource.fetchNews()
            if (result.status == APIResponse.Status.SUCCESS) {
                APIResponse.success(
                    result.data ?: NewsResponse(NewsSubResponse(emptyList()))
                )
            } else {
                throw APIErrorResponse(errorMessage = result.errorMessage.orEmpty())
            }
        }, sourceOfTruth = SourceOfTruth.Companion.of(reader = { _ ->
            newsLocalDataSource.fetchAllNewList().map { itemList ->
                val dataList = ArrayList<NewsResults>()
                itemList.forEach {
                    dataList.add(
                        NewsResults(
                            lang = it.lang,
                            title = it.title,
                            body = it.body,
                            image = it.image,
                            url = it.url ?: ""
                        )
                    )
                }
                val response = NewsResponse(NewsSubResponse(results = dataList))
                APIResponse.success(response)
            }
        }, writer = { _, input ->
            if (input.status == APIResponse.Status.SUCCESS) {
                newsLocalDataSource.insertAllNews(
                    input.data?.articles?.results ?: emptyList()
                )
            }
            // Update Cache Time
            preferenceUtilImpl.updateCacheTime()
        })).cachePolicy(
            MemoryPolicy.builder<String, APIResponse<NewsResponse>>()
                .setExpireAfterWrite(CACHE_EXPIRE_TIME).build()
        ).build()

    override suspend fun fetchNews(): Flow<APIResponse<NewsResponse>> {
        return flow {
            val isCacheExpired = preferenceUtilImpl.isCacheExpired()

            try {
                if (isCacheExpired) {
                    val data = store.fresh("").data
                    emit(
                        APIResponse.success(
                            data ?: NewsResponse(
                                NewsSubResponse(
                                    emptyList()
                                )
                            )
                        )
                    )
                } else {
                    val data = store.get("fetchAlbumDetailRequest").data
                    emit(
                        APIResponse.success(
                            data ?: NewsResponse(
                                NewsSubResponse(
                                    emptyList()
                                )
                            )
                        )
                    )
                }
            } catch (e: APIErrorResponse) {
                emit(APIResponse.error(errorMessage = e.errorMessage))
            }
        }

    }
}