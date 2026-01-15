package com.vbshkn.dreamboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vbshkn.dreamboard.data.remote.RemoteImage

@Composable
fun TestScreen(remoteImage: RemoteImage?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(30.dp)
    ) {
        Text(
            text = remoteImage?.let {
                "id: ${it.id}" +
                        "\ntitle: ${it.title}" +
                        "\npreviewUrl: ${it.previewUrl}" +
                        "\nthumbnailUrl: ${it.thumbnailUrl}"
            } ?: "WAITING...",
            color = Color.White,
        )
    }
}