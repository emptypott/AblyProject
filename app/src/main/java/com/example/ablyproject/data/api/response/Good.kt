package com.example.ablyproject.data.api.response

import com.google.gson.annotations.SerializedName

data class Good(
    val id: Int?,
    val name: String?,
    val image : String?,

    @SerializedName("is_new")
    val isNew :Boolean?,

    @SerializedName("sell_count")
    val sellCount : Int?,

    @SerializedName("actual_price")
    val actualPrice : Int?,
    val price : Int?
)