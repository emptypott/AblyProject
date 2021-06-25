package com.example.ablyproject.presentation

import com.example.ablyproject.di.DaggerAppComponent
import com.example.ablyproject.di.DatabaseModule
import com.example.ablyproject.di.NetworkModule
import com.example.ablyproject.util.PreferenceUtil
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

open class App : DaggerApplication() {

    companion object {
        lateinit var prefs : PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule.instance)
            .databaseModule(DatabaseModule.instance)
            .build()
    }
}