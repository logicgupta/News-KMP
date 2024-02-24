package com.example.core.localStorage.di

import com.example.core.localStorage.PreferenceAPIImpl
import com.example.core.localStorage.PreferenceApi
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings
import org.koin.dsl.module

@OptIn(ExperimentalSettingsApi::class)
val preferenceModule = module {

    single<PreferenceApi> {
        PreferenceAPIImpl(suspendSettings = Settings().toSuspendSettings())
    }

}