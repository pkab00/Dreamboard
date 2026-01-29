package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.Flow

class GetImageListUseCase(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(
        imageFormat: ImageFormat, aiGenerated: Boolean?,
        term: String, page: Int, perPage: Int
    ): Flow<Resource<ImageListEntity>> {
        return imageRepository.getImages(imageFormat, aiGenerated, term, page, perPage)
    }
}