package com.example.portfolio.localdb

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["userId", "restaurantId"])
data class Like(
    val userId: String,
    val restaurantId: String,
    val restaurantImage: String,
    val restaurantName: String
)

@Entity(primaryKeys = ["userId", "restaurantId"])
data class CartWithOrder(
    val userId: String = "",
    val restaurantId: String = "",
    val restaurantName: String ="",
    val menuName: String = "",
    val price: String =""
)

@Entity
data class OrderHistory(
    @PrimaryKey(autoGenerate = true) val keys: Int = 0,
    val orderUserId: String,
    @Embedded val cartWithOrder: CartWithOrder,
    val date: String,
)

/**
 * TODO
 * 설정 하는 목록이 하나밖에 없으므로 shared preference 를 사용하고
 * 차후에 많아진다면 옮기는 것을 고려 해 볼것
 * */
//@Entity
//data class UserOptionState(
//    val notifyOption: Boolean
//)
