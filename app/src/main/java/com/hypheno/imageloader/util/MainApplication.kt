package com.hypheno.imageloader.util

import android.app.Application
import com.hypheno.imageloader.model.network.SearchApiService
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptor
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptorImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        //bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        //bind() from singleton { SearchApiService(instance()) }
    }

}