package com.ggr3ml1n.notesmanager.presentation

import android.app.Application
import com.ggr3ml1n.notesmanager.presentation.di.appModule
import com.ggr3ml1n.notesmanager.presentation.di.dataModule
import com.ggr3ml1n.notesmanager.presentation.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{

            androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(listOf(dataModule, domainModule, appModule))
        }
    }
}