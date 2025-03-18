package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import kotlinx.coroutines.flow.Flow

interface ThirtyFiveCategoryRepository {
    fun getCategories(): Flow<List<ThirtyFiveCategory>>
    fun getProductsByCategory(category: ThirtyFiveCategory): Flow<List<ThirtyFiveProduct>>
}