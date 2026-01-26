package com.vbshkn.dreamboard.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vbshkn.dreamboard.presentation.viewmodel.ImageDetailsViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import com.vbshkn.dreamboard.domain.model.Image

@Composable
fun ImageDetailsScreen(viewModel: ImageDetailsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val memoryCachePolicy by viewModel.memoryCachePolicy.collectAsStateWithLifecycle()
    val discCachePolicy by viewModel.discCachePolicy.collectAsStateWithLifecycle()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading) {
            LoadingContent()
        } else if (state.message != null) {
            ErrorContent(message = state.message)
        } else {
            SuccessContent(
                image = state.image!!,
                memoryCachePolicy = memoryCachePolicy,
                discCachePolicy = discCachePolicy,
                onDownload = viewModel::downloadImageToDevice,
                onShare = viewModel::shareImage
            )
        }
    }
}

@Composable
private fun ImageItem(
    url: String?,
    memoryCachePolicy: CachePolicy,
    discCachePolicy: CachePolicy
) {
    val screenHeight = LocalWindowInfo.current.containerDpSize.height
    val maxImageHeight = screenHeight * 0.5f
    val minImageHeight = screenHeight * 0.2f
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .diskCachePolicy(discCachePolicy)
            .memoryCachePolicy(memoryCachePolicy)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(
                min = minImageHeight,
                max = maxImageHeight
            )
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
    )
}

@Composable
fun DetailsPanel(
    image: Image,
    onDownload: (Image) -> Unit,
    onShare: (Image) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = image.title ?: "No title",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { onDownload(image) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Download")
            }
            Button(onClick = { onShare(image) }) {
                androidx.compose.foundation.Image(
                    imageVector = Icons.Filled.IosShare,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun SuccessContent(
    image: Image,
    memoryCachePolicy: CachePolicy,
    discCachePolicy: CachePolicy,
    onDownload: (Image) -> Unit,
    onShare: (Image) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        ImageItem(
            url = image.path,
            memoryCachePolicy = memoryCachePolicy,
            discCachePolicy = discCachePolicy
        )
        DetailsPanel(
            image = image,
            onDownload = onDownload,
            onShare = onShare,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ErrorContent(message: String?) {
    Text(
        text = message ?: "Unknown error",
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun LoadingContent() {
    CircularProgressIndicator()
}