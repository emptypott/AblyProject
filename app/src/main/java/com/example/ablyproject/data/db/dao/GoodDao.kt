package com.example.ablyproject.data.db.dao

import androidx.annotation.CheckResult
import androidx.room.*
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.GoodEntity
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

@Dao abstract class GoodDao {

    @CheckResult
    @Query("SELECT * FROM goods")
    abstract fun getAllGood() : Flowable<List<GoodEntity>>

    @Query("DELETE FROM goods")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(banners : List<GoodEntity>)

    @Transaction
    open fun clearAndInsert(newGoods : List<GoodEntity>) {
        deleteAll()
        insert(newGoods)
    }
}