package com.botaoap.appnews.domain.mapper

import com.botaoap.appnews.data.remote.response.ArticlesResponse
import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.data.remote.response.SourceResponse
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.model.SourceModel
import com.botaoap.appnews.util.dateFormatterUtil

class NewsListMapper {
    fun getNewsList(
        response: NewsListResponse
    ) : NewsListModel =
        NewsListModel(
            totalResults = response.totalResults,
            articles = getArticles(response.articles)
        )

    private fun getArticles(response: List<ArticlesResponse>) : List<ArticlesModel> =
        response.map {
            ArticlesModel(
                source = getSource(it.source),
                author = it.author,
                title = it.title,
                description = it.description,
                content = it.content,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = dateFormatterUtil(it.publishedAt)
            )
        }

    private fun getSource(response: SourceResponse) : SourceModel =
        SourceModel(
            id = response.id,
            name = response.name
        )
}