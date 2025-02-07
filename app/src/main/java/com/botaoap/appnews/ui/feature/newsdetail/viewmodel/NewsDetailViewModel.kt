package com.botaoap.appnews.ui.feature.newsdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.ui.feature.newsdetail.uistate.NewsDetailUIState

class NewsDetailViewModel(
    private val data: ArticlesModel
) : ViewModel() {
    private val newsDetailUIState = MutableLiveData<NewsDetailUIState>()
    val newsDetailState: LiveData<NewsDetailUIState> = newsDetailUIState

    fun initView() {
        newsDetailUIState.postValue(NewsDetailUIState.ShowView(data))
    }
}