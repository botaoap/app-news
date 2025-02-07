package com.botaoap.appnews.data.remote.response

import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticlesResponse>
)

data class ArticlesResponse(
    @SerializedName("source") val source: SourceResponse,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String
)

data class SourceResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
)