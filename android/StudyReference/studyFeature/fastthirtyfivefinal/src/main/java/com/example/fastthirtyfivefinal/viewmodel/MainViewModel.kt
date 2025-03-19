package com.example.fastthirtyfivefinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveRanking
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveCategoryUseCase
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveMainUseCase
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveBannerDelegate
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveCategoryDelegate
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate
import com.example.fastthirtyfivefinal.model.ThirtyFiveBannerListVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveBannerVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveCarouselVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveEmptyVM
import com.example.fastthirtyfivefinal.model.ThirtyFivePresentationVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveProductVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveRankingVM
import com.example.fastthirtyfivefinal.ui.ThirtyFiveNavigationRouteName
import com.example.fastthirtyfivefinal.util.ThirtyFiveNavigationUtils
import com.example.fastthirtyfivefinal.util.d
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelOld @Inject constructor(
    mainUseCase: ThirtyFiveMainUseCase,
    categoryUseCase: ThirtyFiveCategoryUseCase
): ViewModel(), ThirtyFiveProductDelegate, ThirtyFiveBannerDelegate, ThirtyFiveCategoryDelegate {

    // 무분별한 수정을 막기위해 데이터 변경은 뷰모델 안에서 되도록 함
    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount

    val productList = mainUseCase.getProductList().map(::convertToPresentationVM)
    val categories = categoryUseCase.getCategories()

    fun openSearchForm() {
        "open Search From run".d()
    }

    fun updateColumnCount(count: Int) {
        // viewmodel 에서 unclear 가 호출되면 자동으로 해제 시켜준다.
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    override fun openProduct(product: ThirtyFiveProduct) {

    }

    fun openCarouselProduct(product: ThirtyFiveProduct) {

    }

    fun openRankingProduct(product: ThirtyFiveProduct) {

    }

    override fun openBanner(bannerId: String) {

    }

    fun openBannerList(banner: ThirtyFiveBannerList) {

    }

    override fun openCategory(navHostController: NavHostController, category: ThirtyFiveCategory) {
        ThirtyFiveNavigationUtils.navigate(navHostController, ThirtyFiveNavigationRouteName.CATEGORY, category)
    }

    private fun convertToPresentationVM(list: List<ThirtyFiveBaseModel>): List<ThirtyFivePresentationVM<out ThirtyFiveBaseModel>> {
        return list.map { model ->
            when(model) {
                is ThirtyFiveProduct -> ThirtyFiveProductVM(model, this)
                is ThirtyFiveBanner -> ThirtyFiveBannerVM(model, this)
                is ThirtyFiveBannerList -> ThirtyFiveBannerListVM(model, this)
                is ThirtyFiveCarousel -> ThirtyFiveCarouselVM(model, this)
                is ThirtyFiveRanking -> ThirtyFiveRankingVM(model, this)
                else -> ThirtyFiveEmptyVM(model)
            }
        }
    }

    // 가능하면 하드로 들어가는 값들은 상수로 빼서 관리하는게 좋다.
    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}