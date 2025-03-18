package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.datasource.ThirtyFiveProductDataSource
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.repository.ThirtyFiveRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThirtyFiveRepositoryImpl @Inject constructor(
    private val productDataSource: ThirtyFiveProductDataSource,
): ThirtyFiveRepository {
    override fun getProductList(): Flow<List<ThirtyFiveBaseModel>> = productDataSource.getProducts()
}

