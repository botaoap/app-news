package com.botaoap.appnews.domain.usecase

import com.botaoap.appnews.data.remote.response.NewsListResponse
import com.botaoap.appnews.domain.mapper.NewsListMapper
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.Response

interface GetNewsUseCase {
    fun execute(sources: String?, country: String?): Flow<NewsListState>
}

class GetNewsUseCaseImpl(
    private val repository: Repository,
    private val mapper: NewsListMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetNewsUseCase {
    override fun execute(sources: String?, country: String?): Flow<NewsListState> = flow {
        try {
            repository.getNews(
                sources = sources,
                country = country
            ).let { response ->
                when (response.code()) {
                    200 -> {
                        emit(getSuccess(response))
                    }

                    else -> {
                        emit(NewsListState.Error)
                    }
                }
            }
        } catch (e: Exception) {
            emit(NewsListState.Error)
        }
    }
        .onStart {
            emit(NewsListState.Loading)
        }
        .flowOn(coroutineDispatcher)

    private fun getSuccess(response: Response<NewsListResponse>): NewsListState =
        response.body()?.let { data ->
            when {
                (data.articles.isEmpty()) -> NewsListState.Empty

                else -> NewsListState.Success(data = mapper.getNewsList(data))
            }
        } ?: run { NewsListState.Error }
}

sealed class NewsListState {
    data object Loading : NewsListState()
    data class Success(
        val data: NewsListModel
    ) : NewsListState()

    data object Empty : NewsListState()
    data object Error : NewsListState()
}