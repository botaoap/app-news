package com.botaoap.appnews.ui.feature.newslist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botaoap.appnews.data.contants.Constants
import com.botaoap.appnews.domain.model.NewsListEmptyModel
import com.botaoap.appnews.domain.model.NewsListErrorModel
import com.botaoap.appnews.domain.model.NewsListLoadingModel
import com.botaoap.appnews.domain.usecase.GetNewsUseCase
import com.botaoap.appnews.domain.usecase.NewsListState
import com.botaoap.appnews.ui.feature.newslist.uistate.NewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.RefreshNewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.SelectFilterUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.ShowFooterUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {
    private val newsListUIState = MutableLiveData<NewsListUIState>()
    val newsListState: LiveData<NewsListUIState> = newsListUIState

    private val refreshUIState =
        MutableStateFlow<RefreshNewsListUIState>(RefreshNewsListUIState.Disable)
    val refreshState: StateFlow<RefreshNewsListUIState> = refreshUIState

    private val selectFilterUIState = MutableLiveData<SelectFilterUIState>()
    val selectFilterState: LiveData<SelectFilterUIState> = selectFilterUIState

    private val showFooterUIState = MutableLiveData<ShowFooterUIState>()
    val showFooterState: LiveData<ShowFooterUIState> = showFooterUIState

    private var storeSources: String? = null
    private var storeCountry: String? = null

    fun selectFilter(sources: String?, country: String?) {
        selectFilterUIState.postValue(
            SelectFilterUIState.Selected(
                sources = sources,
                country = country
            )
        )
        storeSources = sources
        storeCountry = country
    }

    fun getNewsList() {
        viewModelScope.launch {
            getNewsUseCase.execute(
                sources = verifyDataSources(),
                country = verifyDataCountry()
            ).collect { state ->
                when (state) {
                    NewsListState.Loading -> {
                        refreshUIState.value = RefreshNewsListUIState.Disable
                        newsListUIState.postValue(
                            NewsListUIState.Loading(
                                listOf(NewsListLoadingModel())
                            )
                        )
                        showFooterUIState.postValue(ShowFooterUIState.Invisible)
                    }

                    is NewsListState.Success -> {
                        refreshUIState.value = RefreshNewsListUIState.Enable
                        newsListUIState.postValue(
                            NewsListUIState.Success(
                                data = state.data
                            )
                        )
                        showFooterUIState.postValue(ShowFooterUIState.Show)
                    }

                    NewsListState.Empty -> {
                        refreshUIState.value = RefreshNewsListUIState.Enable
                        newsListUIState.postValue(
                            NewsListUIState.Empty(
                                listOf(NewsListEmptyModel())
                            )
                        )
                        showFooterUIState.postValue(ShowFooterUIState.Show)
                    }

                    NewsListState.Error -> {
                        refreshUIState.value = RefreshNewsListUIState.Enable
                        newsListUIState.postValue(
                            NewsListUIState.Error(
                                listOf(NewsListErrorModel())
                            )
                        )
                        showFooterUIState.postValue(ShowFooterUIState.Show)
                    }
                }

            }
        }
    }

    private fun verifyDataSources(): String? =
        if (storeSources == null && storeCountry == null) {
            Constants.Path.BBC_NEWS
        } else {
            storeSources
        }

    private fun verifyDataCountry(): String? =
        if (storeSources == null && storeCountry == null) {
            null
        } else if (storeSources == null) {
            storeCountry
        } else {
            null
        }
}