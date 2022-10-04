package com.example.portfolio.localdb

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Like(
    @PrimaryKey(autoGenerate = true) val key: Int,
    val userId: String,
    val restaurantId: String,
    val restaurantImage: Uri,
    val restaurantName: String
)

@Entity
data class UserOptionState(
    val notifyOption: Boolean
)