package com.botaoap.appnews.di

import com.botaoap.appnews.service.HeaderInterceptor
import com.botaoap.appnews.service.KoinIdentifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

object InterceptorModule {
    val modules = module {
        factory(named(KoinIdentifier.Interceptor.X_API_KEY)) { HeaderInterceptor() }
    }
}