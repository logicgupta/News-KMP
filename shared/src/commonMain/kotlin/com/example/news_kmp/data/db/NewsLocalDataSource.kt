package com.example.news_kmp.data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.news_kmp.data.dto.NewsResults
import com.example.news_kmp.shared.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class NewsLocalDataSource(dataSource: NewsDatabase) {

    private val dbQuery = dataSource.newsListQueries


    fun insertAllNews(newsItem: List<NewsResults>) {
        dbQuery.transaction {
            newsItem.forEach {
                dbQuery.insertNewsItem(
                    lang = it.lang,
                    title = it.title,
                    body = it.body,
                    image = it.image,
                    url = it.url
                )
            }
        }
    }

    fun fetchAllNewList() = dbQuery.fetchNew().asFlow().mapToList(Dispatchers.IO)
}