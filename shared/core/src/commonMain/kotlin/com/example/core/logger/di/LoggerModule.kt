package com.example.core.logger.di

import com.example.core.CoreBuildKonfig
import com.example.core.logger.Impl.LoggerApiImpl
import com.example.core.logger.api.LoggerApi
import org.koin.dsl.module

val loggerModule = module {
    single<LoggerApi> {
        LoggerApiImpl(shouldEnableLogs = CoreBuildKonfig.IS_DEBUG)
    }
}