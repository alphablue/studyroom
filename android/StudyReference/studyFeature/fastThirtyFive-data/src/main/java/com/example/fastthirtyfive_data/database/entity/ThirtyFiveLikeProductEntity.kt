package com.example.fastthirtyfive_data.database.entity

import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop

data class ThirtyFiveLikeProductEntity(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: ThirtyFivePrice,
    val category: ThirtyFiveCategory,
    val shop: ThirtyFiveShop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
)

fun ThirtyFiveLikeProductEntity.toDomainModel(): ThirtyFiveProduct {
    return ThirtyFiveProduct(
        productId = productId,
        productName = productName,
        imageUrl = imageUrl,
        price = price,
        category = category,
        shop = shop,
        isNew = isNew,
        isFreeShipping = isFreeShipping
    )
}