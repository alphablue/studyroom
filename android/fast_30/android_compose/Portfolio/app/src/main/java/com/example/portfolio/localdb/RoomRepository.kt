package com.example.portfolio.localdb

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
class RoomRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val roomDB = Room.databaseBuilder(
        context,
        LocalRoomDB::class.java, "LocalDB"
    ).build()

    suspend fun insertLike(
        like: Like
    ) = withContext(Dispatchers.IO) { roomDB.localDao().insertLike(like) }

    suspend fun deleteLike(
        like: Like
    ) = withContext(Dispatchers.IO) {roomDB.localDao().deleteLike(like)}

    suspend fun getAllLike(): List<Like> = withContext(Dispatchers.IO) {
        val getAllList = roomDB.localDao().getAllLike()
        suspendCoroutine {
            it.resume(getAllList)
        }
    }

}