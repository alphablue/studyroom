package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.ThirtyFiveAccountInfo
import com.example.fastthirtyfive_domain.repository.ThirtyFiveAccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ThirtyFiveAccountUseCase @Inject constructor(
    private val accountRepository: ThirtyFiveAccountRepository
) {

    fun getAccountInfo(): StateFlow<ThirtyFiveAccountInfo?> {
        return accountRepository.getAccountInfo()
    }

    suspend fun signInGoogle(accountInfo: ThirtyFiveAccountInfo) {
        accountRepository.signInGoogle(accountInfo)
    }

    suspend fun signOutGoogle() {
        accountRepository.signOutGoogle()
    }
}