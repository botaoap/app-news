package com.botaoap.appnews.data.remote.network

import com.botaoap.appnews.UrlProvider
import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.service.HTTPClient
import com.botaoap.appnews.service.OkHttpClientFactory
import okhttp3.Interceptor
import retrofit2.Response

class WebServiceImpl(
    vararg interceptor: Interceptor
) : WebService {

    private val okHttpClient = OkHttpClientFactory.createHttpClient(*interceptor)
    private val webServiceClient =
        HTTPClient.create(okHttpClient, UrlProvider.BASE_URL, WebService::class)


    override suspend fun getNews(sources: String?, country: String?): Response<NewsListResponse> =
        webServiceClient.getNews(sources, country)
}