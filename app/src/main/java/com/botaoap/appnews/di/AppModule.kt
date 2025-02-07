package com.botaoap.appnews.di

import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.network.WebServiceImpl
import com.botaoap.appnews.data.repository.RepositoryImpl
import com.botaoap.appnews.domain.mapper.NewsListMapper
import com.botaoap.appnews.domain.repository.Repository
import com.botaoap.appnews.domain.usecase.GetNewsUseCase
import com.botaoap.appnews.domain.usecase.GetNewsUseCaseImpl
import com.botaoap.appnews.service.KoinIdentifier
import com.botaoap.appnews.ui.feature.newslist.viewmodel.NewsListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModule {
    val modules = module {
        getWebServiceModule(this)
        getRepositoryModule(this)
        getUseCaseModule(this)
        getMapperModule(this)
        getViewModelModule(this)
    }

    private fun getWebServiceModule(module: Module) {
        with(module) {
            single<WebService> {
                WebServiceImpl(
                    get(named(KoinIdentifier.Interceptor.X_API_KEY))
                )
            }
        }
    }

    private fun getRepositoryModule(module: Module) {
        with(module) {
            factory<Repository> { RepositoryImpl(get()) }
        }
    }

    private fun getUseCaseModule(module: Module) {
        with(module) {
            single<GetNewsUseCase> { GetNewsUseCaseImpl(get(), get(), Dispatchers.IO) }
        }
    }

    private fun getMapperModule(module: Module) {
        with(module) {
            single { NewsListMapper() }
        }
    }

    private fun getViewModelModule(module: Module) {
        with(module) {
            viewModel { NewsListViewModel(get()) }
        }
    }
}