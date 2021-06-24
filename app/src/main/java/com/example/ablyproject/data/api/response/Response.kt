package com.example.ablyproject.data.api.response

import com.example.ablyproject.data.api.response.Banner
import com.example.ablyproject.data.api.response.Good

data class Response(
    val banners : List<Banner>?,
    val goods : List<Good>?
)