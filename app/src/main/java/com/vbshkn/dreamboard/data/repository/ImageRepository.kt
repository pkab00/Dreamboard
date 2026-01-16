package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.data.remote.ImageResource
import com.vbshkn.dreamboard.data.remote.RemoteImageDataSource
import com.vbshkn.dreamboard.util.NetworkResult
import com.vbshkn.dreamboard.util.SafeApiCaller

class ImageRepository(
    private val remoteDataSource: RemoteImageDataSource
) : SafeApiCaller {
    suspend fun getImage(id: Long) : NetworkResult<ImageResource> {
        return safeApiCall { remoteDataSource.getImage(id) }
    }
}