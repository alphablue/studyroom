package com.example.fastthirtyfivefinal.viewmodel.category

import androidx.lifecycle.ViewModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ThirtyFiveCategoryViewModel @Inject constructor(
    private val categoryUseCase: ThirtyFiveCategoryUseCase
): ViewModel() {
    private val _products = MutableStateFlow<List<ThirtyFiveProduct>>(listOf())
    val products: StateFlow<List<ThirtyFiveProduct>> = _products

    suspend fun updateCategory(category: ThirtyFiveCategory) {
        categoryUseCase.getProductsByCategory(category).collectLatest {
            _products.emit(it)
        }
    }

    fun openProduct(product: ThirtyFiveProduct) {

    }
}