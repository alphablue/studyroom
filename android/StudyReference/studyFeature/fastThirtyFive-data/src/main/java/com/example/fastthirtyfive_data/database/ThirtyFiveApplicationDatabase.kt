package com.example.fastthirtyfive_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fastthirtyfive_data.database.dao.ThirtyFiveBasketDao
import com.example.fastthirtyfive_data.database.dao.ThirtyFiveLikeDao
import com.example.fastthirtyfive_data.database.dao.ThirtyFivePurchaseDao
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveBasketProductEntity
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveLikeProductEntity
import com.example.fastthirtyfive_data.database.entity.ThirtyFivePurchaseProductEntity

@Database(
    entities = [
        ThirtyFiveBasketProductEntity::class,
        ThirtyFivePurchaseProductEntity::class,
        ThirtyFiveLikeProductEntity::class
    ],
    version = 1
)
abstract class ThirtyFiveApplicationDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ThirtyFiveApplicationDatabase.db"
    }

    abstract fun thirtyFiveBasketDao(): ThirtyFiveBasketDao
    abstract fun thirtyFivePurchaseDao(): ThirtyFivePurchaseDao
    abstract fun thirtyFiveLikeDao(): ThirtyFiveLikeDao
}