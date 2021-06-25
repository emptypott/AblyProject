package com.example.ablyproject.data.api.response

import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.GoodEntity

fun List<Banner>?.toBannerEntities() : List<BannerEntity> =
    this!!.map {responseBanner ->
        BannerEntity(id = responseBanner.id!!,
            image = responseBanner.image!!)
}

fun List<Good>?.toGoodEntities() : List<GoodEntity> =
    this!!.map {responseGood ->
        GoodEntity(id = responseGood.id!!,
            name = responseGood.name!!,
            image = responseGood.image!!,
            isNew = responseGood.isNew!!,
            sellCount = responseGood.sellCount!!,
            actualPrice = responseGood.actualPrice!!,
            price = responseGood.price!!)
    }