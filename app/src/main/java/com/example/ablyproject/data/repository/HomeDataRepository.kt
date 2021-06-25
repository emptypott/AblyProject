package com.example.ablyproject.data.repository

import androidx.annotation.CheckResult
import com.example.ablyproject.data.api.HomeApi
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.db.HomeDatabase
import com.example.ablyproject.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
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

    @CheckResult
    override fun addGoodData(lastId : Int): Completable {
        return api.getAddGoodData(lastId)
            .doOnSuccess { response ->
                homeDataBase.saveAddGoods(response)
            }
            .subscribeOn(schedulerProvider.io()).toCompletable()
    }

    override val banners: Flowable<List<Banner>> =
        homeDataBase.getAllBanner().toBanners().filter { it.isNotEmpty() }

    override val goods: Flowable<List<Good>> =
        homeDataBase.getAllGood().toGoods().filter { it.isNotEmpty() }

    override val favorites : Flowable<List<Favorite>> =
        homeDataBase.getAllFavorite().toFavorites().filter{it.isNotEmpty()}

    override fun saveId(favoriteId : Int) {
        homeDataBase.saveOneFavorite(favoriteId)
    }

    override fun deleteId(favoriteId: Int) {
        homeDataBase.deleteOneFavorite(favoriteId)
    }
}