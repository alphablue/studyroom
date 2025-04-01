package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.database.dao.ThirtyFiveLikeDao
import com.example.fastthirtyfive_data.database.entity.toLikeProductEntity
import com.example.fastthirtyfive_data.datasource.ThirtyFiveProductDataSource
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveRanking
import com.example.fastthirtyfive_domain.repository.ThirtyFiveRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ThirtyFiveRepositoryImpl @Inject constructor(
    private val productDataSource: ThirtyFiveProductDataSource,
    private val likeDao: ThirtyFiveLikeDao
): ThirtyFiveRepository {
    override fun getProductList(): Flow<List<ThirtyFiveBaseModel>> = productDataSource.getHomeComponents().combine(likeDao.getAll()) { homeComponents, likeList ->
        homeComponents.map { model -> mappingLike(model, likeList.map { it.productId }) }
    }

    override suspend fun likeProduct(product: ThirtyFiveProduct) {
        likeDao.insert(product.toLikeProductEntity())
    }

    private fun mappingLike(baseModel: ThirtyFiveBaseModel, likeProductIds: List<String>): ThirtyFiveBaseModel {
        return when(baseModel) {
            is ThirtyFiveCarousel -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })}
            is ThirtyFiveRanking -> { baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })}
            is ThirtyFiveProduct -> { updateLikeStatus(baseModel, likeProductIds) }
            else -> baseModel
        }
    }

    private fun updateLikeStatus(product: ThirtyFiveProduct, likeProduct: List<String>): ThirtyFiveProduct {
        return product.copy(isLike = likeProduct.contains(product.productId))
    }
}

