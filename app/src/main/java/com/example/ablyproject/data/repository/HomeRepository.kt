package com.example.ablyproject.data.repository

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Good
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface HomeRepository {

    val banners: Flowable<List<Banner>>
    val goods : Flowable<List<Good>>

    @CheckResult fun refreshHomeData() : Completable
//    @CheckResult fun favorite(good : Good) : Single<Boolean>

}