package com.botaoap.appnews.ui.feature.newslist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.botaoap.appnews.domain.model.NewsListErrorModel
import com.botaoap.appnews.domain.model.NewsListLoadingModel
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.model.StatusNewsListEnum
import com.botaoap.appnews.domain.usecase.GetNewsUseCase
import com.botaoap.appnews.domain.usecase.NewsListState
import com.botaoap.appnews.ui.feature.newslist.uistate.NewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.RefreshNewsListUIState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsListViewModelTest {

    private lateinit var newsListViewModel: NewsListViewModel
    private lateinit var getNewsUseCase: GetNewsUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        getNewsUseCase = mockk()
        newsListViewModel = NewsListViewModel(getNewsUseCase)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get news should update state to show list`() = dispatcher.run {
        // Arrange
        val newsListModel: NewsListModel = mockk()
        val success = NewsListState.Success(newsListModel)
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(success) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.newsListState.value

        // Assert
        val expected = NewsListUIState.Success(newsListModel)

        assertEquals(expected, act)
    }

    @Test
    fun `get news should update state to show empty state`() = dispatcher.run {
        // Arrange
        val loadingModel = NewsListLoadingModel(status = StatusNewsListEnum.LOADING)
        val emptyState = NewsListState.Loading
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(emptyState) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.newsListState.value

        // Assert
        val expected = NewsListUIState.Loading(listOf(loadingModel))

        assertEquals(expected, act)
    }

    @Test
    fun `get news should update state to show error`() = runTest {
        // Arrange
        val errorModel = NewsListErrorModel(status = StatusNewsListEnum.ERROR)
        val errorState = NewsListState.Error
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(errorState) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.newsListState.value

        // Assert
        val expected = NewsListUIState.Error(listOf(errorModel))

        assertEquals(expected, act)
    }

    @Test
    fun `get news show state loading and refresh is disable`() = runTest {
        // Arrange
        val loading = NewsListState.Loading
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(loading) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.refreshState.value

        // Assert
        val expected = RefreshNewsListUIState.Disable

        assertEquals(expected, act)
    }

    @Test
    fun `get news show state success and refresh is enable`() = runTest {
        // Arrange
        val newsListModel: NewsListModel = mockk()
        val success = NewsListState.Success(newsListModel)
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(success) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.refreshState.value

        // Assert
        val expected = RefreshNewsListUIState.Enable

        assertEquals(expected, act)
    }

    @Test
    fun `get news show state error and refresh is enable`() = runTest {
        // Arrange
        val errorState = NewsListState.Error
        coEvery { getNewsUseCase.execute(any(), any()) } returns flow { emit(errorState) }

        // Act
        newsListViewModel.getNewsList()
        val act = newsListViewModel.refreshState.value

        // Assert
        val expected = RefreshNewsListUIState.Enable

        assertEquals(expected, act)
    }
}