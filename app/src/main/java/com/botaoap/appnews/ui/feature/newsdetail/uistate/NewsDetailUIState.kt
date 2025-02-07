package com.botaoap.appnews.ui.feature.newsdetail.uistate

import com.botaoap.appnews.domain.model.ArticlesModel

sealed class NewsDetailUIState {
    data class ShowView(
        val data: ArticlesModel
    ) : NewsDetailUIState()
}