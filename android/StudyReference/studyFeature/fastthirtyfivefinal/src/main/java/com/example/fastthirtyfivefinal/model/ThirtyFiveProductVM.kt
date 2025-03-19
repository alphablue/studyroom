package com.example.fastthirtyfivefinal.model

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate

class ThirtyFiveProductVM(
    model: ThirtyFiveProduct,
    private val productDelegate: ThirtyFiveProductDelegate
): ThirtyFivePresentationVM<ThirtyFiveProduct>(model) {

}