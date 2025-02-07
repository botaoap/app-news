package com.botaoap.appnews.data.remote.api

import com.botaoap.appnews.data.remote.response.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    /**
     * @see <a href="https://newsapi.org/docs/endpoints/top-headlines">Documentation</a>
     */
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("sources") sources: String?,
        @Query("country") country: String?
    ): Response<NewsListResponse>
}