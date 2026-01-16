package com.vbshkn.dreamboard.data.remote

class RemoteImageDataSource(private val api: VecteezyApi) {
    suspend fun getImage(id: Long) = api.getImage(id)
}