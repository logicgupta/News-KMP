package com.example.news_kmp.android.di

import com.example.news_kmp.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {


    viewModel {
        NewsViewModel()
    }

}