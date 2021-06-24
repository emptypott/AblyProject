package com.example.ablyproject.data.repository

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.HomeApi
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.db.HomeDatabase
import com.example.ablyproject.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class HomeDataRepository @Inject constructor(
    private val api:HomeApi,
    private val homeDataBase: HomeDatabase,
    private val schedulerProvider: SchedulerProvider
) : HomeRepository {

    @CheckResult
    override fun refreshHomeData(): Completable {
        return api.getHomeData()
            .doOnSuccess {response ->
                homeDataBase.save(response)
            }
            .subscribeOn(schedulerProvider.io()).toCompletable()
    }

    override val banners: Flowable<List<Banner>> =
        homeDataBase.getAllBanner().toBanners().filter { it.isNotEmpty() }

    override val goods: Flowable<List<Good>> =
        homeDataBase.getAllGood().toGoods().filter { it.isNotEmpty() }

}
