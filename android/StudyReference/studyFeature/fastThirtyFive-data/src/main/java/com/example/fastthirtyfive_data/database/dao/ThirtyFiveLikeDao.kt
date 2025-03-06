package com.example.fastthirtyfive_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveLikeProductEntity

@Dao
interface ThirtyFiveLikeDao {

    @Query("SELECT * FROM thirty_five_like")
    suspend fun getAll() : List<ThirtyFiveLikeProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ThirtyFiveLikeProductEntity)

    @Query("DELETE FROM thirty_five_like WHERE productId = :id")
    suspend fun delete(id: String)
}