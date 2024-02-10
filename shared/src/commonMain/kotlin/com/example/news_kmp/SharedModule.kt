package com.example.news_kmp

import com.example.core.logger.di.loggerModule
import com.example.core.networking.di.networkModule
import com.example.news_kmp.di.newHomeModule

fun sharedModule() = networkModule + loggerModule + newHomeModule