package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.database.dao.ThirtyFiveSearchDao
import com.example.fastthirtyfive_data.database.entity.ThirtyFiveSearchKeywordEntity
import com.example.fastthirtyfive_data.database.entity.toDomain
import com.example.fastthirtyfive_data.datasource.ThirtyFiveProductDataSource
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchFilter
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import com.example.fastthirtyfive_domain.repository.ThirtyFiveSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThirtyFiveSearchRepositoryImpl @Inject constructor(
    private val dataSource: ThirtyFiveProductDataSource,
    private val searchDao: ThirtyFiveSearchDao,
): ThirtyFiveSearchRepository {
    override suspend fun search(searchKeyword: ThirtyFiveSearchKeyword, filters: List<ThirtyFiveSearchFilter>): Flow<List<ThirtyFiveProduct>> {
        searchDao.insert(ThirtyFiveSearchKeywordEntity(searchKeyword.keyword))
        return dataSource.getProducts().map { list ->
            list.filter { isAvailableProduct(it, searchKeyword, filters) }
        }
    }

    private fun isAvailableProduct(product: ThirtyFiveProduct, searchKeyword: ThirtyFiveSearchKeyword, filters: List<ThirtyFiveSearchFilter>): Boolean {
        var isAvailable = true
        filters.forEach { isAvailable = isAvailable && it.isAvailableProduct(product) }

        return isAvailable && product.productName.contains(searchKeyword.keyword)
    }

    override fun getSearchKeywords(): Flow<List<ThirtyFiveSearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomain() } }
    }

}