package com.example.fastthirtyfivefinal.viewmodel.category

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveCategoryUseCase
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate
import com.example.fastthirtyfivefinal.model.ThirtyFiveProductVM
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName
import com.example.fastthirtyfivefinal.util.ThirtyFiveNavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ThirtyFiveCategoryViewModel @Inject constructor(
    private val categoryUseCase: ThirtyFiveCategoryUseCase
): ViewModel(), ThirtyFiveProductDelegate {
    private val _products = MutableStateFlow<List<ThirtyFiveProductVM>>(listOf())
    val products: StateFlow<List<ThirtyFiveProductVM>> = _products

    suspend fun updateCategory(category: ThirtyFiveCategory) {
        categoryUseCase.getProductsByCategory(category).collectLatest {
            _products.emit(convertToPresentationVM(it))
        }
    }

    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {
        ThirtyFiveNavigationUtils.navigate(navHostController, ThirtyFiveNavigationRouteName.PRODUCT_DETAIL, product)
    }

    private fun convertToPresentationVM(list: List<ThirtyFiveProduct>): List<ThirtyFiveProductVM> {
        return list.map { ThirtyFiveProductVM(it, this) }
    }
}