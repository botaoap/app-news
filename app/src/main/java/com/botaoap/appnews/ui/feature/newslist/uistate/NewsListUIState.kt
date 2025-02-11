package com.botaoap.appnews.ui.feature.newslist.uistate

import com.botaoap.appnews.domain.model.NewsListEmptyModel
import com.botaoap.appnews.domain.model.NewsListErrorModel
import com.botaoap.appnews.domain.model.NewsListLoadingModel
import com.botaoap.appnews.domain.model.NewsListModel

sealed class NewsListUIState {
    data class Loading(
        val data: List<NewsListLoadingModel>
    ) : NewsListUIState()

    data class Success(
        val data: NewsListModel
    ) : NewsListUIState()

    data class Empty(
        val data: List<NewsListEmptyModel>
    ) : NewsListUIState()

    data class Error(
        val data: List<NewsListErrorModel>
    ) : NewsListUIState()
}

sealed class RefreshNewsListUIState {
    data object Enable : RefreshNewsListUIState()
    data object Disable : RefreshNewsListUIState()
}

sealed class SelectFilterUIState {
    data class Selected(
        val sources: String?,
        val country: String?
    ) : SelectFilterUIState()
}

sealed class ShowFooterUIState {
    data object Hidde : ShowFooterUIState()
    data object Invisible : ShowFooterUIState()
    data object Show : ShowFooterUIState()
}