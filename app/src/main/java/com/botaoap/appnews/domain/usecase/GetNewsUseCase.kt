package com.botaoap.appnews.domain.usecase

import com.botaoap.appnews.domain.mapper.NewsListMapper
import com.botaoap.appnews.domain.model.NewsListModel
import com.botaoap.appnews.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

interface GetNewsUseCase {
    fun execute(sources: String): Flow<StatusNewsList>
}

class GetNewsUseCaseImpl(
    private val repository: Repository,
    private val mapper: NewsListMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : GetNewsUseCase {
    override fun execute(sources: String): Flow<StatusNewsList> = flow {
        try {
            repository.getNews(
                sources = sources
            ).let { response ->
                when (response.code()) {
                    200 -> {
                        response.body()?.let { data ->
                            emit(
                                StatusNewsList.Success(
                                    data = mapper.getNewsList(data)
                                )
                            )
                        }
                    }

                    else -> {
                        emit(StatusNewsList.Error)
                    }
                }
            }
        } catch (e: Exception) {
            emit(StatusNewsList.Error)
        }
    }
        .onStart {
            emit(StatusNewsList.Loading)
        }
        .flowOn(coroutineDispatcher)

}

sealed class StatusNewsList {
    data object Loading : StatusNewsList()
    data class Success(
        val data: NewsListModel
    ) : StatusNewsList()

    data object Error : StatusNewsList()
}