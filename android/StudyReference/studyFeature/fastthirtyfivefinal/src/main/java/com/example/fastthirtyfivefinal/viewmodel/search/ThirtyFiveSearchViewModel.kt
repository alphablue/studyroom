package com.example.fastthirtyfivefinal.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveSearchUseCase
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate
import com.example.fastthirtyfivefinal.model.ThirtyFiveProductVM
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName
import com.example.fastthirtyfivefinal.util.ThirtyFiveNavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirtyFiveSearchViewModel @Inject constructor(
  private val useCase: ThirtyFiveSearchUseCase
): ViewModel(), ThirtyFiveProductDelegate {
    private val _searchResult = MutableStateFlow<List<ThirtyFiveProductVM>>(listOf())
    val searchResult: StateFlow<List<ThirtyFiveProductVM>> = _searchResult
    val searchKeywords = useCase.getSearchKeyword()

    fun search(keyword: String) {
        // onClear 될 때마다 job 에 있는 작업들을 stop 시켜준다.
        viewModelScope.launch {
            useCase.search(ThirtyFiveSearchKeyword(keyword)).collectLatest {
                _searchResult.emit(it.map(::convertToProductVM))
            }
        }
    }

    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {
        ThirtyFiveNavigationUtils.navigate(navHostController, ThirtyFiveNavigationRouteName.PRODUCT_DETAIL, product)
    }

    private fun convertToProductVM(product: ThirtyFiveProduct): ThirtyFiveProductVM {
        return ThirtyFiveProductVM(product, this)
    }
}