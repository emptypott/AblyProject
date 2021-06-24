package com.example.ablyproject.data.db

import androidx.annotation.CheckResult
import androidx.room.RoomDatabase
import com.example.ablyproject.data.api.response.Response
import com.example.ablyproject.data.api.response.toBannerEntities
import com.example.ablyproject.data.api.response.toGoodEntities
import com.example.ablyproject.data.db.dao.BannerDao
import com.example.ablyproject.data.db.dao.GoodDao
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.GoodEntity
import io.reactivex.Flowable
import javax.inject.Inject

class HomeRoomDatabase @Inject constructor(
    private val database : RoomDatabase,
    private val bannerDao: BannerDao,
    private val goodDao: GoodDao
) : HomeDatabase  {

    @CheckResult
    override fun getAllBanner(): Flowable<List<BannerEntity>> = bannerDao.getAllBanner()

    @CheckResult
    override fun getAllGood(): Flowable<List<GoodEntity>> = goodDao.getAllGood()

    override fun save(response: Response) {
        database.runInTransaction {
            bannerDao.clearAndInsert(response.banners.toBannerEntities())
            goodDao.clearAndInsert(response.goods.toGoodEntities())
        }
    }
}