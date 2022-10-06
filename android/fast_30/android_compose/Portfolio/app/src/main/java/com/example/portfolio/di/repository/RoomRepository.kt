package com.example.portfolio.di.repository

import com.example.portfolio.localdb.Like
import com.example.portfolio.localdb.RoomDAO
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Module
@InstallIn(SingletonComponent::class)
class RoomRepository @Inject constructor(
    private val roomDAO: RoomDAO
) {
    suspend fun insertLike(
        like: Like
    ) = withContext(Dispatchers.IO) { roomDAO.insertLike(like) }

    suspend fun deleteLike(
        like: Like
    ) = withContext(Dispatchers.IO) {roomDAO.deleteLike(like)}

    suspend fun getAllLike(): List<Like> = withContext(Dispatchers.IO) {
        val getAllList = roomDAO.getAllLike()
        suspendCoroutine {
            it.resume(getAllList)
        }
    }

}