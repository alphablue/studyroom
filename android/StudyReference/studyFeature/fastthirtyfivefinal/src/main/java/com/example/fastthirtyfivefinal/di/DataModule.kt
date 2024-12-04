package com.example.fastthirtyfivefinal.di

import com.example.fastthirtyfive_data.repository.TempRepositoryImpl
import com.example.fastthirtyfive_domain.repository.TempRepository
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