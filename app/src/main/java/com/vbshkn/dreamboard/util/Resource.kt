package com.vbshkn.dreamboard.util

sealed class Resource<T>(
    val body: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(body = data)
    class Error<T>(data: T?, message: String?) : Resource<T>(data, message)
    class Loading<T>() : Resource<T>()

    fun <R> map(transform: (T) -> R): Resource<R> {
        return when(this) {
            is Success -> Success(data = transform(body!!))
            is Error -> Error(data = null, message = message)
            is Loading -> Loading()
        }
    }
}