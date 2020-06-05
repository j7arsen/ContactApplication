package com.j7arsen.contact.application.global

import android.app.Application
import com.j7arsen.contact.application.BuildConfig
import com.j7arsen.contact.application.global.di.appComponent
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initPicasso()
        initKoin()
    }

    private fun initPicasso() {
        val picasso = Picasso.Builder(this)
            .memoryCache(LruCache(MAX_CACHE_SIZE))
            .loggingEnabled(true)
            .build()
        Picasso.setSingletonInstance(picasso)
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@App)
            modules(appComponent)
        }
    }

    companion object {
        private const val MAX_CACHE_SIZE = 50 * 1024 * 1024
    }

}