package com.vbshkn.dreamboard.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.presentation.viewmodel.SearchViewModel
import com.vbshkn.dreamboard.util.ImageFormat

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onImageClick: (Long) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val searchingText by viewModel.searchingText.collectAsStateWithLifecycle()
        val selectedFormat by viewModel.selectedFormat.collectAsStateWithLifecycle()

        SearchBar(
            text = searchingText,
            supportingText = "Images loaded: ${state.images.size}",
            onValueChange = { viewModel.onSearchTextChange(it) }
        )
        ImageFormatSelectionPanel(
            selectedElement = selectedFormat,
            onClick = { viewModel.onFormatSelected(it) }
        )
        HorizontalDivider(
            modifier = Modifier.padding(10.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            if (searchingText.isEmpty()) {
                EmptySearchResultsPlaceholder()
            }
            else if (state.isLoading && state.images.isEmpty()) {
                LoadingScreen()
            } else if (state.message != null) {
                ErrorScreen(
                    errorMessage = state.message ?: "Unknown error",
                    onRetry = viewModel::onRetry
                )
            } else {
                SuccessScreen(
                    images = state.images,
                    loading = state.isLoading,
                    onImageClick = onImageClick,
                    onLoadMore = viewModel::onLoadMore,
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    text: String,
    supportingText: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        placeholder = { Text("Search") },
        supportingText = { Text(text = supportingText) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}

@Composable
private fun ImageFormatSelectionPanel(
    selectedElement: ImageFormat,
    onClick: (ImageFormat) -> Unit
) {
    val formats = ImageFormat.entries

    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        formats.forEach { format ->
            SegmentedButton(
                selected = selectedElement == format,
                onClick = { onClick(format) },
                shape = SegmentedButtonDefaults.baseShape
            ) {
                Text(
                    text = format.apiName.uppercase(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun EmptySearchResultsPlaceholder() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Start typing your search request",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SuccessScreen(
    images: List<Image>,
    loading: Boolean,
    onImageClick: (Long) -> Unit,
    onLoadMore: () -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 5.dp,
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 7.dp)
    ) {
        items(images, key = { it.id }) { image ->
            RemoteImageItem(
                url = image.path,
                caption = image.title,
                onClick = { onImageClick(image.id) }
            )
        }
        item(span = StaggeredGridItemSpan.FullLine) {
            LoadMoreButton(
                visible = images.isNotEmpty(),
                enable = !loading,
                onClick = { onLoadMore() }
            )
        }
    }
}

@Composable
fun RemoteImageItem(
    url: String?,
    caption: String?,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .data(url)
                .crossfade(true)
                .build(),
            placeholder = ColorPainter(Color.Gray),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clickable(onClick = { onClick() })
        )
        Spacer(Modifier.height(3.dp))
        Text(
            text = caption ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 15.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadMoreButton(
    visible: Boolean,
    enable: Boolean,
    onClick: () -> Unit
) {
    if (visible) {
        Button(
            onClick = { onClick() },
            enabled = enable,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .height(50.dp)
        ) {
            Text(
                text = "Load more",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Something went wrong:\n$errorMessage",
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(7.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retry")
        }
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