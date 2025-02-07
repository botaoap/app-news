package com.botaoap.appnews.domain.model

data class NewsListModel(
    val totalResults: Int,
    val articles: List<ArticlesModel>
)

data class ArticlesModel(
    val source: SourceModel,
    val author: String,
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String?
)

data class SourceModel(
    val id: String,
    val name: String
)