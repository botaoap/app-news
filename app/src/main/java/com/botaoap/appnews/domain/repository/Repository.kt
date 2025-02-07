package com.botaoap.appnews.domain.repository

import com.botaoap.appnews.data.remote.response.NewsListResponse
import retrofit2.Response

interface Repository {

    suspend fun getNews(
        sources: String
    ) : Response<NewsListResponse>
}