package com.example.portfolio.model.uidatamodels

import android.net.Uri
import kotlin.random.Random
import kotlin.random.nextInt

data class NearRestaurantInfo(
    val id: String,
    val imgUri: Uri? = null,
    val callNumber: String = "02-1234-1234",
    val name: String,
    val address: String,
    val lon: Double,
    val lat: Double,
    val rating: Float = Random.nextDouble(1.5, 5.0).toFloat(),
    val deliveryTime: String = run {
        val firstTime = Random.nextInt(1..7)
        val secondTime = Random.nextInt(10..35)

        "${firstTime}분 ~ ${secondTime}분"
    },
    val deliveryTip: String = run {
        val firstTip = Random.nextInt(100..350)
        val secondTip = Random.nextInt(500..4000)

        "배달팁 ${firstTip}원 ~ ${secondTip}원"
    }
)