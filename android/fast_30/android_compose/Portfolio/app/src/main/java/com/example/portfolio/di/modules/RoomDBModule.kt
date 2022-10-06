package com.example.portfolio.di.modules

import android.content.Context
import androidx.room.Room
import com.example.portfolio.localdb.LocalRoomDB
import com.example.portfolio.localdb.RoomDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

        @Singleton
        @Provides
        fun provideAppDatabase(
            @ApplicationContext context: Context
        ): LocalRoomDB = Room
            .databaseBuilder(context, LocalRoomDB::class.java, "localdb")
            .build()

        @Singleton
        @Provides
        fun provideLikeDao(appDatabase: LocalRoomDB): RoomDAO = appDatabase.localDao()

}