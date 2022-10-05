package com.example.portfolio.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDAO {

    @Insert
    suspend fun insertLike(vararg likes: Like)

    @Delete()
    suspend fun deleteLike(vararg like: Like)

    @Query("SELECT * FROM `Like`")
    suspend fun getAllLike(): List<Like>
}