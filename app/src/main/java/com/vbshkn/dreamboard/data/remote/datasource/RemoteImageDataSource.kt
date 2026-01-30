package com.vbshkn.dreamboard.data.remote.datasource

import com.vbshkn.dreamboard.data.remote.api.VecteezyApi
import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val api: VecteezyApi
) {
    suspend fun getImage(id: Long): Response<ImageResponse> {
        return api.getImage(id)
    }

    suspend fun getImages(
        contentType: String, aiGenerated: Boolean?, term: String, page: Int, perPage: Int
    ): Response<ImageListResponse> {
        val apiLimitation = 10_000
        if (page > apiLimitation || perPage > apiLimitation) {
            throw IllegalArgumentException("Parameters cannot exceed $apiLimitation")
        }
        return api.getImages(contentType, aiGenerated, term, page, perPage)
    }
}