package com.example.ablyproject.data.db

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.response.Response
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.GoodEntity
import io.reactivex.Flowable

interface HomeDatabase {
    @CheckResult
    fun getAllBanner() : Flowable<List<BannerEntity>>

    @CheckResult
    fun getAllGood() : Flowable<List<GoodEntity>>

    fun save(response: Response)

}