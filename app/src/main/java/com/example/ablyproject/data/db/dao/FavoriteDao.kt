package com.example.ablyproject.data.db.dao

import androidx.room.*
import com.example.ablyproject.data.db.entity.FavoriteEntity
import io.reactivex.Flowable

@Dao
abstract class FavoriteDao {

    @Query("SELECT * FROM favorite")
    abstract fun getAllFavorites() : Flowable<List<FavoriteEntity>>

    @Query("DELETE FROM favorite WHERE id = :id")
    abstract fun delete(id:Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(favoriteEntity : FavoriteEntity)
}