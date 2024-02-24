package com.example.news_kmp.data.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.news_kmp.DATABASE_NEWS_LIST_NAME_KEY
import com.example.news_kmp.shared.NewsDatabase

actual class DriverFactory(val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            NewsDatabase.Schema,
            context = context,
            DATABASE_NEWS_LIST_NAME_KEY
        )
    }
}