package com.botaoap.appnews.data.remote.network

import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.response.NewsListResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class WebServiceImplTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var webService: WebService

    @Before
    fun setup() {
        mockServer = MockWebServer()
        webService = WebServiceImpl()
    }

    @After
    fun shutDown() {
        mockServer.shutdown()
    }

    /**
     * Getting issue with HTTP FAILED: javax.net.ssl.SSLHandshakeException
     */
    @Test
    fun `should return correct response when status 200`() = runTest {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"status\":\"ok\",\"totalResults\":0,\"articles\":[]}")
        mockServer.enqueue(mockResponse)
        // Act
        val act = webService.getNews("bbc-news", null).body()

        // Assert
        val expected = Response.success(
            NewsListResponse(
                status = "ok",
                totalResults = 0,
                articles = listOf()
            )
        ).body()


        assertEquals(expected, act)
    }
}