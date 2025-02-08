package com.botaoap.appnews.domain.mapper

import com.botaoap.appnews.data.remote.response.ArticlesResponse
import com.botaoap.appnews.data.remote.response.SourceResponse
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.model.SourceModel
import com.botaoap.appnews.domain.model.StatusNewsListEnum
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.botaoap.appnews.data.remote.response.NewsListResponse as NewsListResponse1


class NewsListMapperTest {

    private val mapper = NewsListMapper()
    private lateinit var newsListResponse: NewsListResponse1
    private lateinit var newsListModel: NewsListModel

    @Test
    fun `should mapper news list response to news list model correctly`() = runTest {
        // Arrange
        newsListResponse = NewsListResponse1(
            status = "ok",
            totalResults = 1,
            articles = listOf(
                ArticlesResponse(
                    source = SourceResponse(
                        id = "bbc_news",
                        name = "BBC News"
                    ),
                    author = "Author BBC News",
                    title = "Title BBC News",
                    description = "Description BBC News",
                    url = "https://www.bbc.co.uk/news/articles/cz9e23jlwdko",
                    urlToImage = "https://ichef.bbci.co.uk/ace/branded_news/1200/cpsprodpb/ae79/live/d88d60d0-e4f1-11ef-a319-fb4e7360c4ec.jpg",
                    publishedAt = "2025-02-07T02:22:22.0393729Z",
                    content = "Names can be submitted to the Crown Nominations Commission (CNC), the body charged with nominating the new archbishop.\r\nThe Church said the consultation, which runs until 28 March, was \"an opportunit… [+1658 chars]"
                )
            )
        )
        newsListModel = NewsListModel(
            status = StatusNewsListEnum.OK,
            totalResults = 1,
            articles = listOf(
                ArticlesModel(
                    status = StatusNewsListEnum.OK,
                    source = SourceModel(
                        id = "bbc_news",
                        name = "BBC News"
                    ),
                    author = "Author BBC News",
                    title = "Title BBC News",
                    description = "Description BBC News",
                    url = "https://www.bbc.co.uk/news/articles/cz9e23jlwdko",
                    urlToImage = "https://ichef.bbci.co.uk/ace/branded_news/1200/cpsprodpb/ae79/live/d88d60d0-e4f1-11ef-a319-fb4e7360c4ec.jpg",
                    publishedAt = "07/02/2025",
                    content = "Names can be submitted to the Crown Nominations Commission (CNC), the body charged with nominating the new archbishop.\r\nThe Church said the consultation, which runs until 28 March, was \"an opportunit… [+1658 chars]"
                )
            )
        )

        // Act
        val act = mapper.getNewsList(newsListResponse)

        // Assert
        val expected = newsListModel

        assertEquals(expected, act)
    }
}