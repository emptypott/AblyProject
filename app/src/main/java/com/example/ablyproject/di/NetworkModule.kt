package com.example.ablyproject.di

import com.example.ablyproject.data.api.HomeApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetworkModule {

    companion object {
        val instance = NetworkModule()
    }

    @Singleton @Provides
    fun provideOkHttpClient(@NetworkLogger loggingInterceptor : Set<@JvmSuppressWildcards
    Interceptor>) :
            OkHttpClient =
        OkHttpClient.Builder().apply {
            loggingInterceptor.forEach {
                addNetworkInterceptor(it)
            }
        }.build()

    @RetrofitHomeApi @Singleton @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://d2bab9i9pr8lds.cloudfront.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    @NetworkLogger @Singleton @Provides @IntoSet
    fun provideNetworkLogger() : Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    @Singleton @Provides
    fun provideHomeApi(@RetrofitHomeApi retrofit : Retrofit) : HomeApi {
        return retrofit.create(HomeApi::class.java)
    }
}