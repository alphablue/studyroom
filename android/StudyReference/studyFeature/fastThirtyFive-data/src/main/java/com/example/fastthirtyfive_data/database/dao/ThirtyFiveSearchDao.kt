package com.example.fastthirtyfive_data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveSearchKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThirtyFiveSearchDao {

    @Query("SELECT * FROM thirty_five_search")
    fun getAll(): Flow<List<ThirtyFiveSearchKeywordEntity>> // 데이터가 변경 될때 마다 반환

//    suspend fun getAll2(): List<ThirtyFiveSearchKeywordEntity> // 단발성으로 호출

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ThirtyFiveSearchKeywordEntity)
}