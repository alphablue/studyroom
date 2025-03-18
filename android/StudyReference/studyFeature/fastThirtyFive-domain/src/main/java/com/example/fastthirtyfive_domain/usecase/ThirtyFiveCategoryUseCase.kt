package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.repository.ThirtyFiveCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThirtyFiveCategoryUseCase @Inject constructor(
    private val repository: ThirtyFiveCategoryRepository
) {

    fun getCategories(): Flow<List<ThirtyFiveCategory>> = repository.getCategories()

    fun getProductsByCategory(
        category: ThirtyFiveCategory
    ): Flow<List<ThirtyFiveProduct>> = repository.getProductsByCategory(category)
}