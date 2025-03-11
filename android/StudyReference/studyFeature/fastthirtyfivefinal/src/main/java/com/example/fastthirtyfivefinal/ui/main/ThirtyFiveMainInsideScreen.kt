package com.example.fastthirtyfivefinal.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCarousel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveBannerCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveBannerListCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveCarouselCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveProductCard
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld

@Composable
fun ThirtyFiveMainInsideScreen(
    mainViewModel: MainViewModelOld
) {
    // state를 통해서 viewmodel 을 가져온다?
    val modelList by mainViewModel.productList.collectAsState(initial = listOf())
    val columnCount by mainViewModel.columnCount.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(columnCount)) {
        items(
            count = modelList.size,
            span = {index ->
                val item = modelList[index]
                val spanCount = getSpanCountByTypes(item, columnCount)
                GridItemSpan(spanCount)
            }
        ) {
            when(val item = modelList[it]) {
                is ThirtyFiveProduct -> ThirtyFiveProductCard(product = item) {product -> mainViewModel.openProduct(product) }
                is ThirtyFiveBanner -> ThirtyFiveBannerCard(model = item) { banner -> mainViewModel.openBanner(banner)}
                is ThirtyFiveBannerList -> ThirtyFiveBannerListCard(model = item) {banner -> mainViewModel.openBannerList(banner)}
                is ThirtyFiveCarousel -> ThirtyFiveCarouselCard(item) { product-> mainViewModel.openCarouselProduct(product) }
            }
        }
    }
}

// todo : 현재 ThirtyFiveBaseModel 에서 사용되는 타입들이 여러개 생겼을때 추가 해 줘야되는 곳을 한번에 알기 어렵다. 해결 방법은?
private fun getSpanCountByTypes(
    type: ThirtyFiveBaseModel,
    defaultColumnCount: Int
) : Int {
    return when(type) {
        is ThirtyFiveProduct -> { 1 }
        is ThirtyFiveBanner, is ThirtyFiveBannerList-> { defaultColumnCount }
        else -> { 1 }
    }
}