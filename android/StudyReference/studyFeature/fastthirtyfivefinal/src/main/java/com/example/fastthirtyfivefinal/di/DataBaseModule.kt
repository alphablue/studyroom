package com.example.fastthirtyfivefinal.di

import android.content.Context
import androidx.room.Room
import com.example.fastthirtyfive_data.database.ThirtyFiveApplicationDatabase
import com.example.fastthirtyfive_data.database.dao.ThirtyFiveLikeDao
import com.example.fastthirtyfive_data.database.dao.ThirtyFiveSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ThirtyFiveApplicationDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ThirtyFiveApplicationDatabase::class.java,
            ThirtyFiveApplicationDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideThirtyFiveSearchDao(
        database: ThirtyFiveApplicationDatabase
    ): ThirtyFiveSearchDao {
        return database.thirtyFiveSearchDao()
    }

    @Provides
    @Singleton
    fun provideThirtyLikeDao(
        database: ThirtyFiveApplicationDatabase
    ): ThirtyFiveLikeDao {
        return database.thirtyFiveLikeDao()
    }
}