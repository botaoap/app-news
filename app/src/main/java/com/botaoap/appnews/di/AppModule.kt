package com.botaoap.appnews.di

import com.botaoap.appnews.data.remote.api.WebService
import com.botaoap.appnews.data.remote.network.WebServiceImpl
import com.botaoap.appnews.service.KoinIdentifier
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AppModule {
    val modules = module {
        getWebServiceModule(this)
        getRepositoryModule(this)
    }
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

    }
}