package com.vbshkn.dreamboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vbshkn.dreamboard.data.remote.ImageResource
import com.vbshkn.dreamboard.presentation.viewmodel.TestViewModel
import com.vbshkn.dreamboard.util.NetworkResult

@Composable
fun TestScreen(viewModel: TestViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val state = viewModel.imageResult.collectAsState().value ?: NetworkResult.Loading()
            when(state) {
                is NetworkResult.Success -> {
                    SuccessScreen(state.body)
                }
                is NetworkResult.Error -> {
                    ErrorScreen(state.message ?: "Unknown error occurred")
                }
                is NetworkResult.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }
}

@Composable
private fun SuccessScreen(image: ImageResource?) {
    image ?: return
    val imageUrl = image.previewUrl
    val imageCaption = image.title
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = imageCaption,
            placeholder = ColorPainter(Color.Gray),
            clipToBounds = true
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = imageCaption ?: "No caption",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun ErrorScreen(errorMessage: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
    }
}