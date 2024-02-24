package com.example.news_kmp.data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.news_kmp.DATABASE_NEWS_LIST_NAME_KEY
import com.example.news_kmp.shared.NewsDatabase

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(NewsDatabase.Schema, DATABASE_NEWS_LIST_NAME_KEY)
    }
}