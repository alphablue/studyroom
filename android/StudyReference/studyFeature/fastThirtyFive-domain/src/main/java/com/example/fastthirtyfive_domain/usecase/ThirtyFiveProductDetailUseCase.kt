package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.repository.ThirtyFiveProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThirtyFiveProductDetailUseCase @Inject constructor(
    private val repository: ThirtyFiveProductDetailRepository
) {

    fun getProductDetail(productId: String): Flow<ThirtyFiveProduct> {
        return repository.getProductDetail(productId)
    }
}