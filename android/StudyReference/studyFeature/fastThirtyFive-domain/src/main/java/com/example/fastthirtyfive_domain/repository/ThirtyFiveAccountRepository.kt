package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveAccountInfo
import kotlinx.coroutines.flow.StateFlow

interface ThirtyFiveAccountRepository {
    fun getAccountInfo(): StateFlow<ThirtyFiveAccountInfo?>

    suspend fun signInGoogle(accountInfo: ThirtyFiveAccountInfo)

    suspend fun signOutGoogle()
}