package com.example.ablyproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ablyproject.data.db.dao.BannerDao
import com.example.ablyproject.data.db.dao.GoodDao
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.GoodEntity


@Database(
    entities = [
        (BannerEntity::class),
        (GoodEntity::class)
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bannerDao() : BannerDao
    abstract fun goodDao() : GoodDao
}