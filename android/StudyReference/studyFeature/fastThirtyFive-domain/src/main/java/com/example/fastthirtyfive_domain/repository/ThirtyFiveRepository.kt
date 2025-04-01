package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import kotlinx.coroutines.flow.Flow

interface ThirtyFiveRepository {
    fun getProductList(): Flow<List<ThirtyFiveBaseModel>>

    suspend fun likeProduct(product: ThirtyFiveProduct)
}