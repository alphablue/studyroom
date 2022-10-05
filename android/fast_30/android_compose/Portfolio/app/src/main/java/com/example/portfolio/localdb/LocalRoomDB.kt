package com.example.portfolio.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Like::class],
    version = 1
)
abstract class LocalRoomDB: RoomDatabase() {
    abstract fun localDao(): RoomDAO
}