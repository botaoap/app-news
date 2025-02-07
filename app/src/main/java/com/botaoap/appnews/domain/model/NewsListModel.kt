package com.botaoap.appnews.domain.model

abstract class ACNewsListModel {
    abstract val status: StatusNewsListEnum

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        return (other is ACNewsListModel) &&
                status == other.status
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

data class NewsListModel(
    override val status: StatusNewsListEnum,
    val totalResults: Int,
    val articles: List<ArticlesModel>
) : ACNewsListModel()

data class ArticlesModel(
    override val status: StatusNewsListEnum,
    val source: SourceModel,
    val author: String?,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
) : ACNewsListModel()

data class SourceModel(
    val id: String?,
    val name: String
)

data class NewsListLoadingModel(
    override val status: StatusNewsListEnum = StatusNewsListEnum.LOADING
) : ACNewsListModel()

data class NewsListErrorModel(
    override val status: StatusNewsListEnum = StatusNewsListEnum.ERROR
) : ACNewsListModel()

data class NewsListEmptyModel(
    override val status: StatusNewsListEnum = StatusNewsListEnum.EMPTY
) : ACNewsListModel()

enum class StatusNewsListEnum(val key: String) {
    OK("ok"),
    LOADING("loading"),
    EMPTY("empty"),
    ERROR("error");

    companion object {
        fun from(key: String): StatusNewsListEnum = entries.find { it.key == key } ?: ERROR
    }
}
