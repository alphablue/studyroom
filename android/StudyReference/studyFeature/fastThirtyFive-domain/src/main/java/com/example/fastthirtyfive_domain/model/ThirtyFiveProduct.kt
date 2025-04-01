package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(SERIAL_PRODUCT)
data class ThirtyFiveProduct(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: ThirtyFivePrice,
    val category: ThirtyFiveCategory,
    val shop: ThirtyFiveShop,
    val isNew: Boolean,
    val isLike: Boolean,
    val isFreeShipping: Boolean
) : ThirtyFiveBaseModel()

@Serializable
data class ThirtyFivePrice(
    val originPrice: Int,
    val finalPrice: Int,
    val salesStatus: ThirtyFiveSalesStatus,
)