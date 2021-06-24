package com.example.ablyproject.data.api

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.response.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("home")
    @CheckResult
    fun getHomeData() : Single<Response>
}