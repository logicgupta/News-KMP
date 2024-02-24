package com.example.news_kmp.di

import com.example.news_kmp.data.db.DriverFactory
import org.koin.dsl.module

val androidSharedModule = module {
    single {
        DriverFactory(get()).createDriver()
    }
}