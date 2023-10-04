package com.example.composestudymodule.sampleProject01.di

import com.example.seojin.sampleproject01domainlayer.repository.TempRepository
import com.example.toss.next.sampleproject01datalayer.repository.TempRepositoryImpl
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
    fun bindTempRepository(tempRepositoryImpl: TempRepositoryImpl): TempRepository
}