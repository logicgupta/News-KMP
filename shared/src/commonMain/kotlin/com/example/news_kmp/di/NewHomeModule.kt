package com.example.news_kmp.di

import com.example.news_kmp.data.network.NewsDataSource
import com.example.news_kmp.data.repository.HomeRepositoryImpl
import org.koin.dsl.module


val newHomeModule = module {

    single {
        NewsDataSource(get())
    }

    single {
        HomeRepositoryImpl(get())
    }

}