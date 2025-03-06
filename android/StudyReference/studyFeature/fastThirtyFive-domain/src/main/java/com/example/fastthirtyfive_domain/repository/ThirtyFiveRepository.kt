package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import kotlinx.coroutines.flow.Flow

interface ThirtyFiveRepository {
    fun getProductList(): Flow<List<ThirtyFiveProduct>>
}