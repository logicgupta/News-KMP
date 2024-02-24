package com.example.news_kmp.data.db

import app.cash.sqldelight.db.SqlDriver


expect class DriverFactory {
    fun createDriver(): SqlDriver
}