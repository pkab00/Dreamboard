package com.vbshkn.dreamboard.data.remote.interceptor

import com.vbshkn.dreamboard.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AccountIdInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url
            .newBuilder()
            .toString()
            .replace("%account_id%", BuildConfig.VECTEEZY_ACCOUNT_ID)
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}