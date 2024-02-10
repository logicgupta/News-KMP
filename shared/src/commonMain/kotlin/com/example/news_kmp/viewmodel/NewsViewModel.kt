package com.example.news_kmp.viewmodel

import com.example.core.logger.api.LoggerApi
import com.example.core.networking.util.onError
import com.example.core.networking.util.onLoading
import com.example.core.networking.util.onSuccess
import com.example.core.viewmodel.BaseViewModel
import com.example.core.viewmodel.UiEvent
import com.example.core.viewmodel.UiSideEffect
import com.example.core.viewmodel.UiState
import com.example.news_kmp.data.dto.NewsResults
import com.example.news_kmp.data.repository.HomeRepositoryImpl
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.inject

class NewsViewModel : BaseViewModel<NewsEvent, NewsState, NewsSideEffect>() {

    private val newRepositoryImpl: HomeRepositoryImpl by inject()
    private val loggerApi: LoggerApi by inject()

    companion object {
        const val TAG = "NewsViewModel"
    }

    suspend fun init() {
        subscribeEvents()
    }

    private suspend fun mockAPI() {
        val response = newRepositoryImpl.fetchNews()
        response.collectLatest { newResponse ->

            newResponse.onLoading {
                setState {
                    NewsState.Loading
                }
            }

            newResponse.onSuccess {
                setState {
                    loggerApi.logDWithTag(TAG, it.articles.results.size.toString())
                    NewsState.FetchedNews(it.articles.results)
                }
            }

            newResponse.onError { errorMessage, _ ->
                setState {
                    loggerApi.logDWithTag(TAG, errorMessage)
                    NewsState.FetchedErrorNews(errorMessage)
                }
            }

        }
        setEffect {
            NewsSideEffect.ShowToast
        }
    }

    override fun createInitialState(): NewsState {
        return NewsState.Loading
    }

    override suspend fun handleEvent(event: NewsEvent) {
        when (event) {
            NewsEvent.OnFetchHomePageEvent -> {
                mockAPI()
            }

        }
    }
}


sealed interface NewsState : UiState {
    data object Idle : NewsState
    data object Loading : NewsState
    data class FetchedNews(val data: List<NewsResults>) : NewsState
    data class FetchedErrorNews(val errorMessage: String) : NewsState
}

sealed interface NewsEvent : UiEvent {
    data object OnFetchHomePageEvent : NewsEvent
}

sealed interface NewsSideEffect : UiSideEffect {

    data object ShowToast : NewsSideEffect

}