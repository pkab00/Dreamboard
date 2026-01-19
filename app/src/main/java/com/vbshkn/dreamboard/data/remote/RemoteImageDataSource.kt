package com.vbshkn.dreamboard.data.remote

import com.vbshkn.dreamboard.data.remote.api.VecteezyApi

class RemoteImageDataSource(private val api: VecteezyApi) {
    suspend fun getImage(id: Long) = api.getImage(id)
    suspend fun getImages(term: String, page: Int, perPage: Int) = api.getImages(term, page, perPage)
}