package com.example.fastthirtyfivefinal.viewmodel.product_detail

import androidx.lifecycle.ViewModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThirtyFiveProductDetailViewModel @Inject constructor(
    private val useCase: ThirtyFiveProductDetailUseCase
): ViewModel() {

    private val _product = MutableStateFlow<ThirtyFiveProduct?>(null)
    val product: StateFlow<ThirtyFiveProduct?> = _product

    suspend fun updateProduct(productId: String) {
        useCase.getProductDetail(productId).collect {
            _product.emit(it)
        }
    }

    fun addCart(productId: String) {

    }
}