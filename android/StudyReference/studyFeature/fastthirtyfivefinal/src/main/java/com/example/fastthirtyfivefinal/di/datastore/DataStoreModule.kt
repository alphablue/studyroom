package com.example.fastthirtyfivefinal.di.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.fastthirtyfivefinal.TestingPreferences
import com.example.fastthirtyfivefinal.di.ApplicationScope
import com.example.fastthirtyfivefinal.di.Dispatcher
import com.example.fastthirtyfivefinal.di.FastDispatchers
import com.example.fastthirtyfivefinal.protoSerializer.TestingPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideTestingPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(FastDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        testingPreferencesSerializer: TestingPreferencesSerializer
    ): DataStore<TestingPreferences> =
        DataStoreFactory.create(
            serializer = testingPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = listOf()
        ) {
            context.dataStoreFile("testing_prefs.pb")
        }
}