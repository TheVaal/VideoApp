package com.vaalzebub.next.to.videoapp

import android.app.Application
import com.vaalzebub.next.to.videoapp.di.loadModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(loadModule())
        }
    }
}

