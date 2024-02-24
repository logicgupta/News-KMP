package com.example.news_kmp.android

import android.app.Application
import com.example.news_kmp.android.di.viewModelModule
import com.example.news_kmp.di.androidSharedModule
import com.example.news_kmp.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NewsApplication)
            androidLogger()
            modules(androidSharedModule)
            modules(sharedModule())
            modules(viewModelModule)
        }
    }
}