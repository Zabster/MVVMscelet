package com.zabster.mvvmscelet

import android.app.Application
import com.zabster.mvvmscelet.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            androidFileProperties()
            modules(appModule)
        }
    }

}


// todo add data binding