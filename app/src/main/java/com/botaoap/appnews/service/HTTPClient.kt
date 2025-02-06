package com.botaoap.appnews.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

object HTTPClient {
    private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T : Any> create(okHttpClient: OkHttpClient, baseUrl: String, clazz: KClass<T>): T =
        provideRetrofit(okHttpClient, baseUrl)
            .create(clazz.java)
}