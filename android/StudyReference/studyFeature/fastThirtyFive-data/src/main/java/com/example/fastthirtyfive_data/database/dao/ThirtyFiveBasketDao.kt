package com.example.fastthirtyfive_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveBasketProductEntity

@Dao
interface ThirtyFiveBasketDao {

    @Query("SELECT * FROM thirty_five_basket")
    suspend fun getAll() : List<ThirtyFiveBasketProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ThirtyFiveBasketProductEntity)

    @Query("DELETE FROM thirty_five_basket WHERE productId = :id")
    suspend fun delete(id: String)
}