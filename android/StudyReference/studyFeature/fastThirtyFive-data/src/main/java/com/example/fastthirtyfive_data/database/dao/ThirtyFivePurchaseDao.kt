package com.example.fastthirtyfive_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveLikeProductEntity
import com.example.fastthirtyfive_data.database.entity.ThirtyFivePurchaseProductEntity

@Dao
interface ThirtyFivePurchaseDao {

    @Query("SELECT * FROM thirty_five_purchase_product")
    suspend fun getAll() : List<ThirtyFivePurchaseProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ThirtyFivePurchaseProductEntity)

    @Query("DELETE FROM thirty_five_purchase_product WHERE productId = :id")
    suspend fun delete(id: String)
}