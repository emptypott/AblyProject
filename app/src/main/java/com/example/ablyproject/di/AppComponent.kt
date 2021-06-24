package com.example.ablyproject.di

import android.app.Application
import com.example.ablyproject.presentation.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
//dagger.android 사용을 위한 설정
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    MainActivityBuilder::class,
    ViewModelModule::class
    ])
interface AppComponent :AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application (application : Application) : Builder
        fun networkModule (networkModule: NetworkModule) : Builder
        fun databaseModule (databaseModule: DatabaseModule) : Builder
        fun build() : AppComponent
    }

    override fun inject(app : App)

}