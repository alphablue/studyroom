package com.example.fastthirtyfivefinal.di

import com.example.fastthirtyfive_data.repository.TempRepositoryImpl
import com.example.fastthirtyfive_data.repository.ThirtyFiveCategoryRepositoryImpl
import com.example.fastthirtyfive_data.repository.ThirtyFiveProductDetailRepositoryImpl
import com.example.fastthirtyfive_data.repository.ThirtyFiveRepositoryImpl
import com.example.fastthirtyfive_data.repository.ThirtyFiveSearchRepositoryImpl
import com.example.fastthirtyfive_domain.repository.TempRepository
import com.example.fastthirtyfive_domain.repository.ThirtyFiveCategoryRepository
import com.example.fastthirtyfive_domain.repository.ThirtyFiveProductDetailRepository
import com.example.fastthirtyfive_domain.repository.ThirtyFiveRepository
import com.example.fastthirtyfive_domain.repository.ThirtyFiveSearchRepository
import com.example.fastthirtyfivefinal.util.TimeZoneBroadcastMonitor
import com.example.fastthirtyfivefinal.util.TimeZoneMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ThirtyFiveOldDataModule {

    @Binds
    @Singleton
    fun bindTempRepository(tempRepositoryImpl: TempRepositoryImpl) : TempRepository

    @Binds
    @Singleton
    fun bindsMainRepository(mainRepositoryImpl: ThirtyFiveRepositoryImpl): ThirtyFiveRepository

    @Binds
    @Singleton
    fun bindsCategoryRepository(categoryRepositoryImpl: ThirtyFiveCategoryRepositoryImpl): ThirtyFiveCategoryRepository

    @Binds
    @Singleton
    fun bindsProductDetailRepository(categoryRepositoryImpl: ThirtyFiveProductDetailRepositoryImpl): ThirtyFiveProductDetailRepository

    @Binds
    @Singleton
    fun bindsSearchRepository(categoryRepositoryImpl: ThirtyFiveSearchRepositoryImpl): ThirtyFiveSearchRepository
}

// 강의 에서는 interface 로 정의 했지만, now in 에서는 접근 제한자의 관리를 위해 추상클래스로 관리함
@Module
@InstallIn(SingletonComponent::class)
abstract class ThirtyFiveNewDataModule {

    @Binds
    internal abstract fun bindsTimeZone(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}