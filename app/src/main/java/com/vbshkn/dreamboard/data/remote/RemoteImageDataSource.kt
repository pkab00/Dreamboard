package com.vbshkn.dreamboard.data.remote

import com.vbshkn.dreamboard.data.remote.api.VecteezyApi

class RemoteImageDataSource(private val api: VecteezyApi) {
    suspend fun getImage(id: Long) = api.getImage(id)
    suspend fun getImages(contentType: String, term: String, page: Int, perPage: Int) =
        api.getImages(contentType, term, page, perPage)
}