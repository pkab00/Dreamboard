package com.vbshkn.dreamboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vbshkn.dreamboard.data.remote.ImageResource
import com.vbshkn.dreamboard.presentation.viewmodel.TestViewModel
import com.vbshkn.dreamboard.util.NetworkResult

@Composable
fun TestScreen(viewModel: TestViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(30.dp)
    ) {
        val state = viewModel.imageResult.collectAsState().value ?: NetworkResult.Loading()

        when(state) {
            is NetworkResult.Success -> {
                SuccessScreen(state.body ?: return)
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

@Composable
fun SuccessScreen(image: ImageResource) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Title: ${image.title}",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ErrorScreen(errorMessage: String) {
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
fun LoadingScreen() {
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