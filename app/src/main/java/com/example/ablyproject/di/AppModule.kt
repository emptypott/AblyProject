package com.example.ablyproject.di

import android.app.Application
import android.content.Context
import com.example.ablyproject.data.api.HomeApi
import com.example.ablyproject.data.db.HomeDatabase
import com.example.ablyproject.data.repository.HomeDataRepository
import com.example.ablyproject.data.repository.HomeRepository
import com.example.ablyproject.util.AppSchedulerProvider
import com.example.ablyproject.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module internal object AppModule {

    @Singleton @Provides @JvmStatic
    fun provideContext(application : Application) : Context = application

    @Singleton @Provides @JvmStatic
    fun provideHomeRepository(
        api: HomeApi,
        homeDatabase : HomeDatabase,
        schedulerProvider: SchedulerProvider
    ) : HomeRepository = HomeDataRepository(api, homeDatabase, schedulerProvider)

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

}