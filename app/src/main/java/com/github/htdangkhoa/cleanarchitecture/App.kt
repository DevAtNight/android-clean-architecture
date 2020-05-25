package com.github.htdangkhoa.cleanarchitecture

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.chibatching.kotpref.Kotpref
import com.github.htdangkhoa.cleanarchitecture.di.AppComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Kotpref.init(this)

        Utils.init(this)

        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR
            )

            androidContext(this@App)

            modules(AppComponent.components)
        }
    }
}
