package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.repository.ThirtyFiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThirtyFiveMainUseCase @Inject constructor(
    private val mainRepository: ThirtyFiveRepository
) {
    fun getProductList(): Flow<List<ThirtyFiveProduct>> {
        return mainRepository.getProductList()
    }
}