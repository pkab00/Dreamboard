package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.data.remote.RemoteImageDataSource
import com.vbshkn.dreamboard.data.remote.mapper.ImageListMapper.toDomain
import com.vbshkn.dreamboard.data.remote.mapper.ImageMapper.toDomain
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.domain.repository.ImageRepository
import com.vbshkn.dreamboard.util.ImageFormat
import com.vbshkn.dreamboard.util.Resource
import com.vbshkn.dreamboard.util.SafeApiCaller
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ImageRepositoryImpl(
    private val remoteDataSource: RemoteImageDataSource
) : ImageRepository, SafeApiCaller {
    override fun getImage(id: Long): Flow<Resource<ImageEntity>> = flow {
        emit(Resource.Loading())
        val callResult = safeApiCall { remoteDataSource.getImage(id) }
        emit(callResult.map { it.toDomain() })
    }

    override fun getImages(
        imageFormat: ImageFormat, aiGenerated: Boolean?,
        term: String, page: Int, perPage: Int
    ): Flow<Resource<ImageListEntity>> = flow {
        emit(Resource.Loading())
        val limitation = 10_000
        if (page > limitation || perPage > limitation) {
            emit(Resource.Error(data = null, message = "Parameters cannot exceed $limitation"))
            return@flow
        } else {
            val callResult = safeApiCall {
                remoteDataSource.getImages(imageFormat.apiName, aiGenerated, term, page, perPage)
            }
            emit(callResult.map { it.toDomain() })
        }
    }
}