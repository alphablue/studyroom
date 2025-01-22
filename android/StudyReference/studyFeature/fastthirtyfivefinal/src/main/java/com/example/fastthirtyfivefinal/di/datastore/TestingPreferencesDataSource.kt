package com.example.fastthirtyfivefinal.di.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.example.fastthirtyfivefinal.TestingPreferences
import com.example.fastthirtyfivefinal.copy
import javax.inject.Inject

// 관련 참조 자료 : https://developer.android.com/topic/libraries/architecture/datastore
class TestingPreferencesDataSource @Inject constructor(
    private val testingPreferences: DataStore<TestingPreferences>
) {

    suspend fun setTestingData() {
        try {
            testingPreferences.updateData {
                // 공식 문서에서 제시하는 방법
                it.toBuilder()
                    .setTestOk(true)
                    .setTestInt(30)
                    .build()

                // now in 에서 제공하는 방법
                it.copy {
                    testOk = true
                    testInt = 19
                }
            }
        } catch (e: IOException) {
            Log.e("DataStoreError", "Fail Data Update", e)
        }
    }
}