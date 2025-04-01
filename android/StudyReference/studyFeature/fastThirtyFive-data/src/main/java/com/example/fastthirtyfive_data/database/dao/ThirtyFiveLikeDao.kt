package com.example.fastthirtyfive_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveLikeProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThirtyFiveLikeDao {

    @Query("SELECT * FROM thirty_five_like")
    fun getAll() : Flow<List<ThirtyFiveLikeProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ThirtyFiveLikeProductEntity)

    @Query("DELETE FROM thirty_five_like WHERE productId = :id")
    suspend fun delete(id: String)
}