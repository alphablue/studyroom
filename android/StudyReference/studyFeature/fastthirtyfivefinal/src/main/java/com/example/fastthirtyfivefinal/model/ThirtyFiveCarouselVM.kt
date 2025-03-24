package com.example.fastthirtyfivefinal.model

import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate

class ThirtyFiveCarouselVM(
    model: ThirtyFiveCarousel,
    private val productDelegate: ThirtyFiveProductDelegate
): ThirtyFivePresentationVM<ThirtyFiveCarousel>(model) {

    fun openCarouselProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {
        productDelegate.openProduct(navHostController, product)
        sendCarouselLog()
    }

    private fun sendCarouselLog() {

    }
}