package com.example.fastthirtyfivefinal.model

import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName
import com.example.fastthirtyfivefinal.util.ThirtyFiveNavigationUtils

class ThirtyFiveProductVM(
    model: ThirtyFiveProduct,
    private val productDelegate: ThirtyFiveProductDelegate
): ThirtyFivePresentationVM<ThirtyFiveProduct>(model), ThirtyFiveProductDelegate by productDelegate {
    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {
        ThirtyFiveNavigationUtils.navigate(navHostController, ThirtyFiveNavigationRouteName.PRODUCT_DETAIL, product)
    }
}