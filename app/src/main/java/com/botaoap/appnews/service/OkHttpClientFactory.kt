package com.botaoap.appnews.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {
    fun createHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        for (interceptor in interceptors) {
            okHttpClient.addInterceptor(interceptor)
        }

        return okHttpClient.build()
    }
}