package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ThirtyFiveShop(
    val shopId: String,
    val shopName: String,
    val imageUrl: String
)