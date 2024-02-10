package com.example.news_kmp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.ui.theme.GreenPrimaryLight
import com.example.news_kmp.data.dto.NewsResults
import com.example.news_kmp.viewmodel.NewsEvent
import com.example.news_kmp.viewmodel.NewsSideEffect
import com.example.news_kmp.viewmodel.NewsState
import com.example.news_kmp.viewmodel.NewsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: NewsViewModel) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        TopAppBar(
            title = {
                Text(text = "News APP", color = GreenPrimaryLight)
            }, modifier = Modifier.fillMaxWidth()
        )

        LaunchedEffect(Unit) {
            viewModel.init()
        }

        LaunchedEffect(Unit) {
            viewModel.effect.collect {
                when (it) {
                    is NewsSideEffect.ShowToast -> {

                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.setEvent(NewsEvent.OnFetchHomePageEvent)
        }
        val uiState = viewModel.uiState.collectAsState()
        when (uiState.value) {

            NewsState.Idle -> {
                // SO Nothing
            }

            is NewsState.FetchedErrorNews -> {
                Text(text = "Error")
            }

            is NewsState.FetchedNews -> {
                LazyColumn {

                    val data = (uiState.value as NewsState.FetchedNews).data
                    items(data) {
                        NewsItem(it)
                        Divider(modifier = Modifier.fillMaxWidth())
                    }

                }
            }

            NewsState.Loading -> {
                NewsFullScreenLoader()
            }
        }


    }
}

@Composable
fun NewsItem(data: NewsResults) {
    Row {

        KamelImage(
            resource = asyncPainterResource(data = data.image.orEmpty()),
            contentDescription = null,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp).width(100.dp).height(100.dp)
                .align(Alignment.CenterVertically),
        )

        Column {
            Text(
                text = data.title,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, end = 16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = data.body,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp, bottom = 4.dp, end = 16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}

@Composable
fun NewsFullScreenLoader() {

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }

}

