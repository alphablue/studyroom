package com.example.fastthirtyfivefinal.delegate

import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct

interface ThirtyFiveProductDelegate {
    fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct)
}