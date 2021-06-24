package com.example.ablyproject.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds fun bindsModelFactory(factory : ViewModelFactory) : ViewModelProvider.Factory
}