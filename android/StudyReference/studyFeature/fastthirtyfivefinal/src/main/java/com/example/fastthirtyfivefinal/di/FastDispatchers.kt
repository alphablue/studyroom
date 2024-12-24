package com.example.fastthirtyfivefinal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

// 이렇게 어노테이션을 정의하고 각 어노테이션의 작동(inject) 하는 방법에 대해서 정의 해 둘 필요가 있다.
// 그것을 module을 정의해서 표현 한다. DispatchersModule 참조
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val fastDispatcher: FastDispatchers)

enum class FastDispatchers {
    Default,
    IO
}

// Dispatcher annotation 에 대해서 동작을 정의 하는 부분이다.
@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(FastDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(FastDispatchers.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}