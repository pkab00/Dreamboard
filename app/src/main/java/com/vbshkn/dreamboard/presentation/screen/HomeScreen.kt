package com.vbshkn.dreamboard.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val imageMap by viewModel.imageMap.collectAsStateWithLifecycle()
    val memoryCachePolicy by viewModel.memoryCachePolicy.collectAsStateWithLifecycle()
    val discCachePolicy by viewModel.discCachePolicy.collectAsStateWithLifecycle()

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 7.dp)
    ) {
        imageMap.forEach { (key, value) ->
            item {
                ImageGrid(
                    title = key.replaceFirstChar { it.uppercaseChar() },
                    images = value,
                    memoryCachePolicy = memoryCachePolicy,
                    discCachePolicy = discCachePolicy
                )
            }
        }
        item { Spacer(Modifier.height(10.dp)) }
    }
}

@Composable
fun ImageGrid(
    title: String,
    images: List<Image>,
    memoryCachePolicy: CachePolicy,
    discCachePolicy: CachePolicy
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImageGridCaption(title)
        HorizontalDivider(
            Modifier.padding(
                vertical = 10.dp,
                horizontal = 5.dp
            )
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier.height(100.dp)
        ) {
            if (images.isEmpty()) {
                item { CircularProgressIndicator() }
            } else {
                items(images, key = { it.id }) {
                    ImageGridItem(
                        url = it.path,
                        memoryCachePolicy = memoryCachePolicy,
                        discCachePolicy = discCachePolicy
                    )
                }
            }
        }
    }
}

@Composable
private fun ImageGridCaption(caption: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = caption,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 7.dp)
        )
    }
}

@Composable
private fun ImageGridItem(
    url: String?,
    memoryCachePolicy: CachePolicy,
    discCachePolicy: CachePolicy
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = Modifier.size(100.dp),
        placeholder = ColorPainter(Color.Gray),
        contentScale = ContentScale.Crop,
        clipToBounds = true
    )
}