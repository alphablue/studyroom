package com.example.portfolio.localdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDAO {

    @Insert
    suspend fun insertLike(vararg likes: Like)

    @Delete
    suspend fun deleteLike(vararg like: Like)

    @Query("SELECT * FROM `Like`")
    suspend fun getAllLike(): List<Like>

    @Insert
    suspend fun insertCart(vararg carts: CartWithOrder)

    @Delete
    suspend fun deleteCart(vararg cart: CartWithOrder)

    @Query("SELECT * FROM `cartwithorder`")
    suspend fun getAllCarts(): List<CartWithOrder>

    @Insert
    suspend fun insertOrderHistory(vararg carts: OrderHistory)

    @Query("SELECT * FROM 'orderhistory' WHERE orderUserId=:userId")
    suspend fun getAllHistory(userId: String): List<OrderHistory>
}