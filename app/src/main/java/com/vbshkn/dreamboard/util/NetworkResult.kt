package com.vbshkn.dreamboard.util

sealed class NetworkResult<T>(
    val body: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(body = data)
    class Error<T>(data: T?, message: String?) : NetworkResult<T>(data, message)
    class Loading<T>() : NetworkResult<T>()
}