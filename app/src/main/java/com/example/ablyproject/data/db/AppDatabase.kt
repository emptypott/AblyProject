package com.example.ablyproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ablyproject.data.db.dao.BannerDao
import com.example.ablyproject.data.db.dao.FavoriteDao
import com.example.ablyproject.data.db.dao.GoodDao
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.FavoriteEntity
import com.example.ablyproject.data.db.entity.GoodEntity

@Database(
    entities = [
        (BannerEntity::class),
        (GoodEntity::class),
        (FavoriteEntity::class)
    ],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bannerDao() : BannerDao
    abstract fun goodDao() : GoodDao
    abstract fun favoriteDao() : FavoriteDao
}