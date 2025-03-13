package com.example.fastthirtyfivefinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveMainUseCase
import com.example.fastthirtyfivefinal.util.d
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelOld @Inject constructor(
    mainUseCase: ThirtyFiveMainUseCase
): ViewModel() {

    // 무분별한 수정을 막기위해 데이터 변경은 뷰모델 안에서 되도록 함
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount

    val productList = mainUseCase.getProductList()

    fun openSearchForm() {
        "open Search From run".d()
    }

    fun updateColumnCount(count: Int) {
        // viewmodel 에서 unclear 가 호출되면 자동으로 해제 시켜준다.
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    fun openProduct(product: ThirtyFiveProduct) {

    }

    fun openCarouselProduct(product: ThirtyFiveProduct) {

    }

    fun openRankingProduct(product: ThirtyFiveProduct) {

    }

    fun openBanner(banner: ThirtyFiveBanner) {

    }

    fun openBannerList(banner: ThirtyFiveBannerList) {

    }

    // 가능하면 하드로 들어가는 값들은 상수로 빼서 관리하는게 좋다.
    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}