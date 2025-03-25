package com.example.fastthirtyfivefinal.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.CATEGORY
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.MAIN_CATEGORY
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.MAIN_HOME
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.MAIN_MY_PAGE
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.PRODUCT_DETAIL
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName.SEARCH

sealed class ThirtyFiveNavigationItem(
    open val route: String,
) {
    sealed class MainNav(
        override val route: String,
        val icon: ImageVector,
        val name: String
    ) : ThirtyFiveNavigationItem(route) {
        data object Home : MainNav(MAIN_HOME, Icons.Filled.Home, "Main")
        data object Category : MainNav(MAIN_CATEGORY, Icons.Filled.Star, "Category")
        data object MyPage : MainNav(MAIN_MY_PAGE, Icons.Filled.AccountBox, "MyPage")

        companion object {
            fun isMainRoute(route: String?): Boolean {
                return when(route) {
                    MAIN_HOME, MAIN_CATEGORY, MAIN_MY_PAGE -> true
                    else -> false
                }
            }
        }
    }

    data class ThirtyFiveCategoryNav(val category: ThirtyFiveCategory): ThirtyFiveNavigationItem(CATEGORY)

    // 랜딩 페이지
    data class ThirtyFiveProductDetailNav(val product: ThirtyFiveProduct): ThirtyFiveNavigationItem(PRODUCT_DETAIL)

    data object ThirtyFiveSearchNav: ThirtyFiveNavigationItem(SEARCH)
}

object ThirtyFiveNavigationRouteName {
    const val MAIN_HOME = "main_home"
    const val MAIN_CATEGORY = "main_category"
    const val MAIN_MY_PAGE = "main_my_page"
    const val CATEGORY = "category"
    const val PRODUCT_DETAIL = "product_detail"
    const val SEARCH = "search"
}