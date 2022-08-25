package com.example.portfolio.di.modules

import com.example.portfolio.viewmodel.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DispatcherModule {

    @Binds
    fun bindDispatcherProvider(provider: DispatcherProviderImpl): DispatcherProvider
}

@Singleton
class DispatcherProviderImpl @Inject constructor(): DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}