package com.example.ablyproject.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.ablyproject.presentation.MainActivity
import com.example.ablyproject.presentation.fragment.FavoriteFragment
import com.example.ablyproject.presentation.fragment.HomeFragment
import com.example.ablyproject.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module interface MainActivityModule {

    @Binds
    fun providesAppCompatActivity(mainActivity : MainActivity) : AppCompatActivity

    @ContributesAndroidInjector fun contributeHomeFragment() : HomeFragment

    @ContributesAndroidInjector fun contributeFavoriteFragment() : FavoriteFragment

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindHomeViewModel(
        homeViewModel: MainViewModel
    ) : ViewModel
}