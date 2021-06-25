package com.example.ablyproject.data.repository

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import io.reactivex.Completable
import io.reactivex.Flowable

interface HomeRepository {

    val banners: Flowable<List<Banner>>
    val goods : Flowable<List<Good>>
    val favorites : Flowable<List<Favorite>>

    @CheckResult fun refreshHomeData() : Completable

    @CheckResult fun addGoodData(lastId : Int) : Completable

    fun saveId(favoriteId : Int)

    fun deleteId(favoriteId : Int)
}