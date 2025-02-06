package com.botaoap.appnews.application

import android.app.Application
import com.botaoap.appnews.di.AppModule
import com.botaoap.appnews.di.InterceptorModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
                    AppModule.modules,
                    InterceptorModule.modules
                )
            )
        }
    }
}