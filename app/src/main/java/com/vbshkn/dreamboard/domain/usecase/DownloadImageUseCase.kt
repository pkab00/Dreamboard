package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.service.ImageDownloader
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(
    private val imageDownloader: ImageDownloader
) {
    operator fun invoke(imageEntity: ImageEntity): Long {
        return imageDownloader.downloadImage(imageEntity)
    }
}