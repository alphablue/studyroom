package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate

class ThirtyFiveCarouselVM(
    model: ThirtyFiveCarousel,
    private val productDelegate: ThirtyFiveProductDelegate
): ThirtyFivePresentationVM<ThirtyFiveCarousel>(model) {

    fun openCarouselProduct(product: ThirtyFiveProduct) {
        productDelegate.openProduct(product)
        sendCarouselLog()
    }

    private fun sendCarouselLog() {

    }
}