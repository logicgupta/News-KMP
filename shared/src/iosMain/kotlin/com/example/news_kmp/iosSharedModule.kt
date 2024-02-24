package com.example.news_kmp

import com.example.news_kmp.data.db.DriverFactory
import org.koin.dsl.module

val iosSharedModule = module {

    single {

        DriverFactory().createDriver()
    }

}