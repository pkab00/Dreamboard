package com.vbshkn.dreamboard.util

import retrofit2.Response

interface SafeApiCaller {
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>) : Resource<T> {
        try {
            val result = call()
            if (result.isSuccessful) {
                val body = result.body()
                body?.let {
                    return Resource.Success(body)
                }
                return errorMessage("Body is empty")
            } else {
                return errorMessage("${result.code()} : ${result.errorBody()}")
            }
        } catch (e: Exception) {
            return errorMessage(e.message.toString())
        }
    }

    private fun <T> errorMessage(message: String) : Resource.Error<T> =
        Resource.Error(data = null, message = "Api call failed: $message")
}