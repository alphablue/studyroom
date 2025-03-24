package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.datasource.ThirtyFiveProductDataSource
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.repository.ThirtyFiveProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThirtyFiveProductDetailRepositoryImpl @Inject constructor(
    private val dataSource: ThirtyFiveProductDataSource
) : ThirtyFiveProductDetailRepository {
    override fun getProductDetail(productId: String): Flow<ThirtyFiveProduct> {
        return dataSource.getProducts().map { products ->
            products.filterIsInstance<ThirtyFiveProduct>().first { it.productId == productId }
        }
    }
}