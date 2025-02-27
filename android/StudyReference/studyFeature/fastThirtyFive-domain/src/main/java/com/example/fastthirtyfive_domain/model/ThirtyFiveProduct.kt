package com.example.fastthirtyfive_domain.model

data class ThirtyFiveProduct(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: ThirtyFivePrice,
    val category: ThirtyFiveCategory,
    val shop: ThirtyFiveShop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
)

data class ThirtyFivePrice(
    val originPrice: Int,
    val finalPrice: Int,
    val salesStatus: ThirtyFiveSalesStatus,
)