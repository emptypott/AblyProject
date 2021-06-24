package com.example.ablyproject.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "goods")
data class GoodEntity(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "image")
    val image : String?,
    @ColumnInfo(name = "isNew")
    val isNew :Boolean?,
    @ColumnInfo(name = "sellCount")
    val sellCount : Int?,
    @ColumnInfo(name = "actualPrice")
    val actualPrice : Int?,
    @ColumnInfo(name = "price")
    val price : Int?
    )