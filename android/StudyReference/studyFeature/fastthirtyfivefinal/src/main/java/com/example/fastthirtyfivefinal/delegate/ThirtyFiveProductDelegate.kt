package com.example.fastthirtyfivefinal.delegate

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct

interface ThirtyFiveProductDelegate {
    fun openProduct(product: ThirtyFiveProduct)
}