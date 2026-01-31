package com.vbshkn.dreamboard.data.remote.datasource

import com.vbshkn.dreamboard.data.remote.api.VecteezyApi
import com.vbshkn.dreamboard.data.remote.model.ImageListResponse
import com.vbshkn.dreamboard.data.remote.model.ImageResponse
import com.vbshkn.dreamboard.domain.datasource.ImageDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val api: VecteezyApi
) : ImageDataSource {
    override suspend fun getImage(id: Long): ImageResponse {
        val response = api.getImage(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        }
        else throw Exception("API Error: ${response.code()}")
    }

    override suspend fun getImages(
        contentType: String, aiGenerated: Boolean?, term: String, page: Int, perPage: Int
    ): ImageListResponse {
        val apiLimitation = 10_000
        if (page > apiLimitation || perPage > apiLimitation) {
            throw IllegalArgumentException("Parameters cannot exceed $apiLimitation")
        }
        val response = api.getImages(contentType, aiGenerated, term, page, perPage)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        }
        else throw Exception("API Error: ${response.code()}")
    }
}