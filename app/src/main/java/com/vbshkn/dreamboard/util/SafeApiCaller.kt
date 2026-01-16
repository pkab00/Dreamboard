package com.vbshkn.dreamboard.util

import retrofit2.Response

interface SafeApiCaller {
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>) : NetworkResult<T> {
        try {
            val result = call()
            if (result.isSuccessful) {
                val body = result.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
                return errorMessage("Body is empty")
            } else {
                return errorMessage("${result.code()} : ${result.errorBody()}")
            }
        } catch (e: Exception) {
            return errorMessage(e.message.toString())
        }
    }

    private fun <T> errorMessage(message: String) : NetworkResult.Error<T> =
        NetworkResult.Error(data = null, message = "Api call failed: $message")
}