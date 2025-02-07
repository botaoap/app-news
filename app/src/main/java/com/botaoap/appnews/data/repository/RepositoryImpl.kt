package com.botaoap.appnews.data.repository

import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.domain.repository.Repository
import retrofit2.Response

class RepositoryImpl(
    private val webService: WebService
) : Repository {

    override suspend fun getNews(sources: String?, country: String?): Response<NewsListResponse> =
        webService.getNews(sources, country)
}