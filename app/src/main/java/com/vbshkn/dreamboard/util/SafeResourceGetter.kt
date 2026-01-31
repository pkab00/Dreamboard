package com.vbshkn.dreamboard.util


interface SafeResourceGetter {
    suspend fun <T> getResourceSafe(call: suspend () -> T) : Resource<T> {
        try {
            val result = call()
            return Resource.Success(result)
        } catch (e: Exception) {
            return errorMessage(e.message.toString())
        }
    }

    private fun <T> errorMessage(message: String) : Resource.Error<T> =
        Resource.Error(data = null, message = "Api call failed: $message")
}