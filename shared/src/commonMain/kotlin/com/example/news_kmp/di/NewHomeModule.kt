package com.example.news_kmp.di

import com.example.news_kmp.data.cache.PreferenceUtilImpl
import com.example.news_kmp.data.db.NewsLocalDataSource
import com.example.news_kmp.data.network.NewsDataSource
import com.example.news_kmp.data.repository.HomeRepositoryImpl
import com.example.news_kmp.shared.NewsDatabase
import org.koin.dsl.module


val newHomeModule = module {

    single {
        NewsDataSource(get())
    }
    single {
        NewsDatabase(get())
    }

    single {
        NewsLocalDataSource(get())
    }

    single {
        PreferenceUtilImpl(get())
    }

    single {
        HomeRepositoryImpl(get(), get(), get())
    }

}