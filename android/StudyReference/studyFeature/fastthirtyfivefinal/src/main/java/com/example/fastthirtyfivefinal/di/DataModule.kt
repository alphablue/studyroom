package com.example.fastthirtyfivefinal.di

import com.example.fastthirtyfive_data.repository.TempRepositoryImpl
import com.example.fastthirtyfive_domain.repository.TempRepository
import com.example.fastthirtyfivefinal.util.TimeZoneBroadcastMonitor
import com.example.fastthirtyfivefinal.util.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindTempRepository(tempRepositoryImpl: TempRepositoryImpl) : TempRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ThirtyFiveNewDataModule {

    @Binds
    internal abstract fun bindsTimeZone(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}