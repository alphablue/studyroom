package com.example.fastthirtyfive_data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.example.fastthirtyfive_domain.model.ThirtyFiveAccountInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ThirtyFivePreferenceDatasource @Inject constructor(
    @ApplicationContext context: Context,
) {
    companion object {
        private const val PREFERENCE_NAME = "thirty_five_preference_name"
        private const val ACCOUNT_INFO = "thirty_five_account_info"
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private val jsonBuilder by lazy { Json }

    /**
     * 아래와 같이 private 으로 한 이유는 capsulation 을 위한 목적이다.
     * public 로 함수들을 공개하게 되면 이 클래스에서의 작업은 줄겠지만 외부에서 상수를 수시로 호출, 활용 하게 되면서
     * 복잡해 지는 문제가 있다.
     * */
    private fun putString(key: String, data: String?) {
        editor.putString(key, data)
        editor.apply()
    }

    private fun putBoolean(key: String, data: Boolean) {
        editor.putBoolean(key, data)
        editor.apply()
    }

    private fun putInt(key: String, data: Int) {
        editor.putInt(key, data)
        editor.apply()
    }

    private fun getString(key: String, defValue: String = "") : String {
        return prefs.getString(key, defValue)!!
    }

    private fun getBoolean(key: String, defValue: Boolean = false) : Boolean {
        return prefs.getBoolean(key, defValue)
    }

    private fun getInt(key: String, defValue: Int) : Int {
        return prefs.getInt(key, defValue)
    }

    fun putAccountInfo(accountInfo: ThirtyFiveAccountInfo) {
        putString(ACCOUNT_INFO, jsonBuilder.encodeToString(accountInfo))
    }

    fun removeAccountInfo() {
        putString(ACCOUNT_INFO, null)
    }

    fun getAccountInfo(): ThirtyFiveAccountInfo? {
        return if(getString(ACCOUNT_INFO).isNotBlank()){
            jsonBuilder.decodeFromString(getString(ACCOUNT_INFO))
        } else null
    }
}