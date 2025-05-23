package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.database.dao.ThirtyFiveLikeDao
import com.example.fastthirtyfive_data.database.entity.toLikeProductEntity
import com.example.fastthirtyfive_data.datasource.ThirtyFiveProductDataSource
import com.example.fastthirtyfive_domain.model.Bag
import com.example.fastthirtyfive_domain.model.Dress
import com.example.fastthirtyfive_domain.model.FashionAccessories
import com.example.fastthirtyfive_domain.model.Outerwear
import com.example.fastthirtyfive_domain.model.Pants
import com.example.fastthirtyfive_domain.model.Shoes
import com.example.fastthirtyfive_domain.model.Skirt
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.Top
import com.example.fastthirtyfive_domain.repository.ThirtyFiveCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThirtyFiveCategoryRepositoryImpl @Inject constructor(
    private val productDataSource: ThirtyFiveProductDataSource,
    private val likeDao: ThirtyFiveLikeDao
) : ThirtyFiveCategoryRepository {
    override fun getCategories(): Flow<List<ThirtyFiveCategory>> = flow {
        emit(
            listOf(
                Top, Bag, Outerwear,
                Dress, FashionAccessories, Pants,
                Skirt, Shoes
            )
        )
    }

    override fun getProductsByCategory(category: ThirtyFiveCategory): Flow<List<ThirtyFiveProduct>> {
        return productDataSource.getHomeComponents().map { productList ->

            // filterIsInstance 는 해당 타입과 같은 것만 골라서 필터링 할 수 있도록 한다.
            productList.filterIsInstance<ThirtyFiveProduct>()
                .filter { product ->
                    product.category.categoryId == category.categoryId
                }
        }.combine(likeDao.getAll()) { products, likeList ->
            products.map { product -> updateLikeStatus(product, likeList.map { it.productId }) }
        }
    }

    override suspend fun likeProduct(product: ThirtyFiveProduct) {
        likeDao.insert(product.toLikeProductEntity())
    }

    private fun updateLikeStatus(product: ThirtyFiveProduct, likeProduct: List<String>): ThirtyFiveProduct {
        return product.copy(isLike = likeProduct.contains(product.productId))
    }
}