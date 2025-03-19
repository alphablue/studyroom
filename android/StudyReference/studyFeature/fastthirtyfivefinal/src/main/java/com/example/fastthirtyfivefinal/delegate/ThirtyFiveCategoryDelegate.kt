package com.example.fastthirtyfivefinal.delegate

import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory

interface ThirtyFiveCategoryDelegate {
    fun openCategory(navHostController: NavHostController, category: ThirtyFiveCategory)
}