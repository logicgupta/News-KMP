package com.example.news_kmp.data.cache

interface PreferenceUtilApi {

    suspend fun setCacheTime(timeInMilliseconds: Long)

    suspend fun getCacheTime(): Long?

    suspend fun clearCacheTime()

}