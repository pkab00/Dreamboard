package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.data.remote.RemoteImageDataSource
import com.vbshkn.dreamboard.data.remote.mapper.ImageListMapper.toDomain
import com.vbshkn.dreamboard.data.remote.mapper.ImageMapper.toDomain
import com.vbshkn.dreamboard.domain.model.Image
import com.vbshkn.dreamboard.domain.model.ImageList
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import com.vbshkn.dreamboard.util.SafeApiCaller
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepository(
    private val remoteDataSource: RemoteImageDataSource
) : SafeApiCaller {
    fun getImage(id: Long): Flow<Resource<Image>> = flow {
        emit(Resource.Loading())
        val callResult = safeApiCall { remoteDataSource.getImage(id) }
        emit(callResult.map { it.toDomain() })
    }

    fun getImages(
        imageFormat: ImageFormat,
        term: String,
        page: Int,
        perPage: Int
    ): Flow<Resource<ImageList>> = flow {
        emit(Resource.Loading())
        val limitation = 10_000
        if (page > limitation || perPage > limitation) {
            emit(Resource.Error(data = null, message = "Parameters cannot exceed $limitation"))
            return@flow
        } else {
            val callResult = safeApiCall {
                remoteDataSource.getImages(imageFormat.apiName, term, page, perPage)
            }
            emit(callResult.map { it.toDomain() })
        }
    }
}