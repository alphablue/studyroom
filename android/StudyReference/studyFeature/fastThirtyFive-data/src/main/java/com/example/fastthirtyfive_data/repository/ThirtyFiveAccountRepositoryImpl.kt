package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.datasource.ThirtyFivePreferenceDatasource
import com.example.fastthirtyfive_domain.model.ThirtyFiveAccountInfo
import com.example.fastthirtyfive_domain.repository.ThirtyFiveAccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ThirtyFiveAccountRepositoryImpl @Inject constructor(
    private val preferenceDatasource: ThirtyFivePreferenceDatasource
) : ThirtyFiveAccountRepository {
    private val accountInfoFlow = MutableStateFlow(preferenceDatasource.getAccountInfo())

    override fun getAccountInfo(): StateFlow<ThirtyFiveAccountInfo?> {
        return accountInfoFlow
    }

    override suspend fun signInGoogle(accountInfo: ThirtyFiveAccountInfo) {
        preferenceDatasource.putAccountInfo(accountInfo)
        accountInfoFlow.emit(accountInfo)
    }

    override suspend fun signOutGoogle() {
        preferenceDatasource.removeAccountInfo()
        accountInfoFlow.emit(null)
    }
}