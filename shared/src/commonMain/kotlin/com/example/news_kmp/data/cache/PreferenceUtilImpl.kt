package com.example.news_kmp.data.cache

import com.example.core.localStorage.PreferenceApi
import com.example.news_kmp.CACHE_EXPIRE_TIME
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.until

class PreferenceUtilImpl(private val preferenceApi: PreferenceApi) : PreferenceUtilApi {
    override suspend fun setCacheTime(timeInMilliseconds: Long) {
        preferenceApi.putLong(CACHE_TIME_KEY, timeInMilliseconds)
    }

    override suspend fun getCacheTime(): Long? {
        return preferenceApi.getLong(CACHE_TIME_KEY)
    }

    override suspend fun clearCacheTime() {
        return preferenceApi.remove(CACHE_TIME_KEY)
    }

    suspend fun updateCacheTime() {
        setCacheTime(Clock.System.now().toEpochMilliseconds())
    }

    suspend fun isCacheExpired(): Boolean {
        val lastWrittenTime = getCacheTime()
        return if (lastWrittenTime != null) {
            val currentTimeStamp = Clock.System.now()
            val lastWrittenTimeStamp = Instant.fromEpochMilliseconds(lastWrittenTime)

            val secondsSinceLastWritten = lastWrittenTimeStamp.until(
                currentTimeStamp, DateTimeUnit.SECOND, TimeZone.currentSystemDefault()
            )
            val maxCacheExpiryInSeconds = CACHE_EXPIRE_TIME.inWholeSeconds
            secondsSinceLastWritten > maxCacheExpiryInSeconds
        } else {
            true
        }

    }

    companion object {
        const val CACHE_TIME_KEY = "CACHE_TIME_KEY"
    }

}