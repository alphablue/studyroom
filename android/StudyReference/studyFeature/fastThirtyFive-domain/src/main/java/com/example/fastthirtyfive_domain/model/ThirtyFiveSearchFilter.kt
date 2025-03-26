package com.example.fastthirtyfive_domain.model

// TODO : 함수의 상속과 실행을 위한 구조를 잘 살펴보는게 좋을뜻
sealed class ThirtyFiveSearchFilter(
    open val type: ThirtyFiveType
) {

    enum class ThirtyFiveType {
        PRICE, CATEGORY
    }

    open fun isAvailableProduct(product: ThirtyFiveProduct): Boolean {
        return true
    }

    open fun clear() { }

    data class PriceFilter(val priceRange: Pair<Float, Float>, var selectedRange: Pair<Float, Float>? = null): ThirtyFiveSearchFilter(ThirtyFiveType.PRICE) {
        override fun isAvailableProduct(product: ThirtyFiveProduct): Boolean {
            // 필터가 없는 경우 전체를 보여줄 것으로 생각된다.
            return selectedRange == null || product.price.finalPrice.toFloat() in (selectedRange?.first ?: 0f)..(selectedRange?.second ?: 0f)
        }

        override fun clear() {
            selectedRange = null
        }
    }

    data class CategoryFilter(val categories: List<ThirtyFiveCategory>, var selectedCategory: ThirtyFiveCategory? = null) : ThirtyFiveSearchFilter(ThirtyFiveType.CATEGORY) {
        override fun isAvailableProduct(product: ThirtyFiveProduct): Boolean {
            return product.category.categoryId == selectedCategory?.categoryId
        }

        override fun clear() {
            selectedCategory = null
        }
    }
}