package com.example.ablyproject.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banners")
data class BannerEntity (
    @PrimaryKey
    var id : Int,
    @ColumnInfo(name = "image")
    var image : String
)