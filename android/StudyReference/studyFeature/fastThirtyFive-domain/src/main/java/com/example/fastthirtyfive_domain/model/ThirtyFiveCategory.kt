package com.example.fastthirtyfive_domain.model

sealed class ThirtyFiveCategory(
    val categoryId: String,
    val categoryName: String,
) {
    data object Top: ThirtyFiveCategory("1", "상의")
    data object Outerwear: ThirtyFiveCategory("2", "아우터")
    data object Dress: ThirtyFiveCategory("3", "원피스")
    data object Pants: ThirtyFiveCategory("4", "바지")
    data object Skirt: ThirtyFiveCategory("5", "치마")
    data object Shoes: ThirtyFiveCategory("6", "신발")
    data object Bag: ThirtyFiveCategory("7", "가방")
    data object FashionAccessories: ThirtyFiveCategory("8", "패션잡화")
}