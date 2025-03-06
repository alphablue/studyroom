package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Serializable
data class ThirtyFiveProduct(
    val type: String,
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: ThirtyFivePrice,
    val category: ThirtyFiveCategory,
    val shop: ThirtyFiveShop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
)

@Serializable
data class ThirtyFivePrice(
    val originPrice: Int,
    val finalPrice: Int,
    val salesStatus: ThirtyFiveSalesStatus,
)