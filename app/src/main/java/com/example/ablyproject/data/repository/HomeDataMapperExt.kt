package com.example.ablyproject.data.repository

import android.annotation.SuppressLint
import androidx.annotation.CheckResult
import androidx.annotation.VisibleForTesting
import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Favorite
import com.example.ablyproject.data.api.response.Good
import com.example.ablyproject.data.db.entity.BannerEntity
import com.example.ablyproject.data.db.entity.FavoriteEntity
import com.example.ablyproject.data.db.entity.GoodEntity
import io.reactivex.Flowable

fun BannerEntity.toBanner() : Banner = Banner(
    id = id,
    image = image
)

fun GoodEntity.toGood() : Good = Good(
    id = id,
    name = name,
    image = image,
    isNew = isNew,
    sellCount = sellCount,
    actualPrice = actualPrice,
    price = price
)

@SuppressLint("VisibleForTests")
@CheckResult
fun Flowable<List<BannerEntity>>.toBanners() : Flowable<List<Banner>> = map {
    bannerEntities -> bannerEntities.toBanners()
}

@SuppressLint("VisibleForTests")
@VisibleForTesting
fun List<BannerEntity>.toBanners() = map {Banner(it.id, it.image)}

@SuppressLint("VisibleForTests")
@CheckResult
fun Flowable<List<GoodEntity>>.toGoods() : Flowable<List<Good>> = map {
        goodEntities -> goodEntities.toGoods()
}

@SuppressLint("VisibleForTests")
@CheckResult
fun Flowable<List<FavoriteEntity>>.toFavorites() : Flowable<List<Favorite>> = map {
        favoriteEntities -> favoriteEntities.toFavorites()
}

@SuppressLint("VisibleForTests")
@VisibleForTesting
fun List<GoodEntity>.toGoods() = map {
    Good(
        it.id,
        it.name,
        it.image,
        it.isNew,
        it.sellCount,
        it.actualPrice,
        it.price
    )
}

@SuppressLint("VisibleForTests")
@VisibleForTesting
fun List<FavoriteEntity>.toFavorites() = map { Favorite(it.id) }

