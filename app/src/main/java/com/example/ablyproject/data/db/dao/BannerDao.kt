package com.example.ablyproject.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.*
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.db.entity.BannerEntity
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language


@Dao
abstract class BannerDao {

    @CheckResult
    @Query("SELECT * FROM banners")
    abstract fun getAllBanner() : Flowable<List<BannerEntity>>

    @Query("DELETE FROM banners")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(banners : List<BannerEntity>)

    @Transaction open fun clearAndInsert(newBanners : List<BannerEntity>) {
        deleteAll()
        insert(newBanners)
    }
}