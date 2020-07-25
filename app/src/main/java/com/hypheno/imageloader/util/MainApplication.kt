package com.hypheno.imageloader.util

import android.app.Application
import com.hypheno.imageloader.model.db.PhotoDao
import com.hypheno.imageloader.model.db.PhotoDatabase
import com.hypheno.imageloader.model.network.SearchApiService
import com.hypheno.imageloader.model.network.datasource.PhotoDataSource
import com.hypheno.imageloader.model.network.datasource.PhotoDataSourceImpl
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptor
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptorImpl
import com.hypheno.imageloader.model.repository.PhotoRepository
import com.hypheno.imageloader.model.repository.PhotoRepositoryImpl
import com.hypheno.imageloader.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { PhotoDatabase(instance()) }
        bind() from singleton { instance<PhotoDatabase>().photoDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }

        bind() from singleton { SearchApiService(instance()) }

        bind<PhotoDataSource>() with singleton { PhotoDataSourceImpl(instance()) }

        bind<PhotoRepository>() with singleton { PhotoRepositoryImpl(instance(), instance()) }

        bind() from provider { MainViewModelFactory(instance()) }
    }

}