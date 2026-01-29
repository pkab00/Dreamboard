package com.vbshkn.dreamboard.domain.usecase

import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.util.Resource
import kotlinx.coroutines.flow.Flow

class GetImageUseCase(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(id: Long): Flow<Resource<ImageEntity>> {
        return imageRepository.getImage(id)
    }
}