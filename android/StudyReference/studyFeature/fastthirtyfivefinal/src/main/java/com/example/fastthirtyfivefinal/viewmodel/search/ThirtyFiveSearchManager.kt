package com.example.fastthirtyfivefinal.viewmodel.search

import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchFilter
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.max
import kotlin.math.min

/**
 * 키워드 저장, 필터 저장, 검색 결과 저장, 필터에 따라 업데이트 등의 기능 제공
 * */
class ThirtyFiveSearchManager {
    private val _filters = MutableStateFlow<List<ThirtyFiveSearchFilter>>(listOf())
    val filters: StateFlow<List<ThirtyFiveSearchFilter>> = _filters

    var searchKeyword = ThirtyFiveSearchKeyword("")
        private set

    /**
     * 검색어와 검색어에 대한 필터가 되지 않은 데이터를 가져오고 가격의 범위에 해당하는 상품만 추출
     * */
    suspend fun initSearchManager(newSearchKeyword: String, searchResult: List<ThirtyFiveProduct>) {
        val categories = mutableListOf<ThirtyFiveCategory>()
        var minPrice = Int.MAX_VALUE
        var maxPrice = Int.MIN_VALUE

        searchResult.forEach { product ->
            // 1. data class 의 값만을 가지고 판단을 하는 경우 틀리는 경우가 있을 수 있기 때문에 로직 수정필요
//            if(!categories.contains(it.category)) {
//                categories.add(it.category)
//            }

            // 2. 로직의 변경
            if(categories.find { it.categoryId == product.category.categoryId } == null) {
                categories.add(product.category)
            }

            minPrice = min(minPrice, product.price.finalPrice)
            maxPrice = max(maxPrice, product.price.finalPrice)
        }

        _filters.emit(listOf(
            ThirtyFiveSearchFilter.PriceFilter(minPrice.toFloat() to maxPrice.toFloat()),
            ThirtyFiveSearchFilter.CategoryFilter(categories)
        ))

        searchKeyword = ThirtyFiveSearchKeyword(newSearchKeyword)
    }

    // filter 만 업데이트
    suspend fun updateFilter(filter: ThirtyFiveSearchFilter) {
        val currentFilter = filters.value

        _filters.emit(currentFilter.map {
            if(it is ThirtyFiveSearchFilter.PriceFilter && filter is ThirtyFiveSearchFilter.PriceFilter) {
                it.selectedRange = filter.selectedRange
            }

            if(it is ThirtyFiveSearchFilter.CategoryFilter && filter is ThirtyFiveSearchFilter.CategoryFilter) {
                it.selectedCategory = filter.selectedCategory
            }

            it
        })
    }

    fun clearFilter() {
        filters.value.forEach { it.clear() }
    }

    // 현재 filter 반환
    fun currentFilters(): List<ThirtyFiveSearchFilter> = filters.value
}