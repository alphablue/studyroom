package com.example.fastthirtyfive_data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fastthirtyfive_data.database.converter.ThirtyFiveLikeConverter
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop

@Entity(tableName = "thirty_five_like")
@TypeConverters(ThirtyFiveLikeConverter::class)
data class ThirtyFiveLikeProductEntity(
    @PrimaryKey val productId: String,
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