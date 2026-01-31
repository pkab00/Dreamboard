package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.data.remote.datasource.RemoteImageDataSource
import com.vbshkn.dreamboard.data.remote.mapper.ImageListMapper.toDomain
import com.vbshkn.dreamboard.data.remote.mapper.ImageMapper.toDomain
import com.vbshkn.dreamboard.domain.datasource.ImageDataSource
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import com.vbshkn.dreamboard.util.SafeResourceGetter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: ImageDataSource
) : ImageRepository, SafeResourceGetter {
    override fun getImage(id: Long): Flow<Resource<ImageEntity>> = flow {
        emit(Resource.Loading())
        val imageResource = getResourceSafe { remoteDataSource.getImage(id) }
        emit(imageResource.map { it.toDomain() })
    }

    override fun getImages(
        imageFormat: ImageFormat, aiGenerated: Boolean?,
        term: String, page: Int, perPage: Int
    ): Flow<Resource<ImageListEntity>> = flow {
        emit(Resource.Loading())
        val imageResource = getResourceSafe {
            remoteDataSource.getImages(imageFormat.apiName, aiGenerated, term, page, perPage)
        }
        emit(imageResource.map { it.toDomain() })
    }
}