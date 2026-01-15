package com.vbshkn.dreamboard.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface VecteezyApi {
    @GET("/v2/%account_id%/resources/{id}")
    suspend fun getImage(@Path("id") id: Int) : RemoteImage
}