package com.botaoap.appnews.data.repository

import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


class RepositoryImplTest {

    private lateinit var repository: Repository
    private lateinit var webService: WebService
    private lateinit var newsListResponse: NewsListResponse
    private lateinit var exception: HttpException

    @Before
    fun setup() {
        webService = mockk()
        repository = RepositoryImpl(webService)
    }

    @Test
    fun `get news with bcc-news should return success`() = runTest {
        // Arrange
        newsListResponse = mockk()

        // Act
        coEvery { webService.getNews("bbc-news", null) } returns Response.success(newsListResponse)

        // Assert
        val result = repository.getNews("bbc-news", null)
        assert(result.isSuccessful)
        assertEquals(newsListResponse, result.body())
    }

    @Test
    fun `get news with country us should return success`() = runTest {
        // Arrange
        newsListResponse = mockk()

        // Act
        coEvery { webService.getNews(null, "us") } returns Response.success(newsListResponse)

        // Assert
        val result = repository.getNews(null, "us")
        assert(result.isSuccessful)
        assertEquals(newsListResponse, result.body())
    }

    @Test
    fun `get news should return error`() = runTest {
        // Arrange
        exception = mockk()

        // Act
        coEvery { webService.getNews(null, null) } throws exception

        // Assert
        Assert.assertThrows(HttpException::class.java) {
            runBlocking {
                // Act
                repository.getNews(null, null)
            }
        }
    }
}