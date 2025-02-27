package com.example.fastthirtyfive_data.database.entity

import androidx.room.Entity
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop

@Entity(tableName = "thirty_five_purchase_product")
data class ThirtyFivePurchaseProductEntity(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: ThirtyFivePrice,
    val category: ThirtyFiveCategory,
    val shop: ThirtyFiveShop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
)

fun ThirtyFivePurchaseProductEntity.toDomainModel(): ThirtyFiveProduct {
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