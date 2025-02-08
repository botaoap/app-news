package com.botaoap.appnews.domain.usecase

import com.botaoap.appnews.data.remote.response.ArticlesResponse
import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.domain.mapper.NewsListMapper
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GetNewsUseCaseImplTest {

    private lateinit var repository: Repository
    private lateinit var mapper: NewsListMapper
    private lateinit var useCase: GetNewsUseCase
    private lateinit var newsListResponse: NewsListResponse
    private lateinit var newsListModel: NewsListModel
    private lateinit var articlesResponse: List<ArticlesResponse>
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        repository = mockk()
        mapper = mockk()
        useCase = GetNewsUseCaseImpl(repository, mapper, dispatcher)
    }

    @Test
    fun `should return success when request`() = runTest {
        // Arrange
        newsListResponse = mockk(relaxed = true)
        articlesResponse = mockk(relaxed = true)
        newsListModel = mockk()

        val response: Response<NewsListResponse> = Response.success(mockk())

        coEvery { repository.getNews("bbc-news", null) } returns response
        coEvery { response.body()?.articles } returns articlesResponse
        coEvery { response.body()?.copy() } returns newsListResponse
        coEvery { mapper.getNewsList(any()) } returns newsListModel

        // Act
        val act = useCase.execute("bbc-news", null).toList()

        // Assert
        val expected = listOf(
            NewsListState.Loading,
            NewsListState.Success(newsListModel)
        )

        assertEquals(expected, act)
    }

    @Test
    fun `should return empty state when status code is 200`() = runTest {
        // Arrange
        newsListResponse = mockk(relaxed = true)
        articlesResponse = mockk(relaxed = true)
        newsListModel = mockk()

        val response: Response<NewsListResponse> = Response.success(mockk())

        coEvery { repository.getNews("bbc-news", null) } returns response
        coEvery { response.body()?.articles } returns listOf()
        coEvery { response.body()?.copy() } returns newsListResponse
        coEvery { mapper.getNewsList(any()) } returns newsListModel

        // Act
        val act = useCase.execute("bbc-news", null).toList()

        // Assert
        val expected = listOf(
            NewsListState.Loading,
            NewsListState.Empty
        )

        assertEquals(expected, act)
    }

    @Test
    fun `should return error when status code is not 200`() = runTest {
        // Arrange
        val errorBody: ResponseBody = mockk {
            every { contentType() } returns "application.json".toMediaTypeOrNull()
            every { contentLength() } returns 0L
            every { string() } returns "{}"
        }
        val response: Response<NewsListResponse> = Response.error(501, errorBody)

        coEvery { repository.getNews(any(), any()) } returns response

        // Act
        val act = useCase.execute(any(), any()).toList()

        // Assert
        val expected = listOf(
            NewsListState.Loading,
            NewsListState.Error
        )

        assertEquals(expected, act)
    }
}