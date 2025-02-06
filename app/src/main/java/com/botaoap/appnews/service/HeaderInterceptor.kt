package com.botaoap.appnews.service

import com.botaoap.appnews.ApiKey
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${ApiKey.API_KEY_TOKEN}")
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}