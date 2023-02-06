package com.example.marveluniverse

import android.app.Application
import com.example.marveluniverse.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelUniverseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MarvelUniverseApplication)
            modules(
                listOf(
                    module
                )
            )
        }

        appInstance = this
    }

    companion object {
        private var appInstance: MarvelUniverseApplication? = null

        fun getInstance(): MarvelUniverseApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure application class on AndroidManifest.xml")
            }

            return appInstance as MarvelUniverseApplication
        }
    }
}