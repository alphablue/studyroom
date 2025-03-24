package com.example.fastthirtyfivefinal.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfivefinal.model.ThirtyFiveBannerListVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveBannerVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveCarouselVM
import com.example.fastthirtyfivefinal.model.ThirtyFivePresentationVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveProductVM
import com.example.fastthirtyfivefinal.model.ThirtyFiveRankingVM
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveBannerCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveBannerListCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveCarouselCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveProductCard
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveRankingCard
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld

@Composable
fun ThirtyFiveMainHomeScreen(
    navHostController: NavHostController,
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
                is ThirtyFiveProductVM -> ThirtyFiveProductCard(navHostController, viewModel = item)
                is ThirtyFiveBannerVM -> ThirtyFiveBannerCard(viewModel = item)
                is ThirtyFiveBannerListVM -> ThirtyFiveBannerListCard(viewModel = item)
                is ThirtyFiveCarouselVM -> ThirtyFiveCarouselCard(navHostController, viewModel = item)
                is ThirtyFiveRankingVM -> ThirtyFiveRankingCard(navHostController, viewModel = item)
            }
        }
    }
}

// todo : 현재 ThirtyFiveBaseModel 에서 사용되는 타입들이 여러개 생겼을때 추가 해 줘야되는 곳을 한번에 알기 어렵다. 해결 방법은?
private inline fun <reified  T : ThirtyFiveBaseModel> getSpanCountByTypes(
    type: ThirtyFivePresentationVM<out T>,
    defaultColumnCount: Int
) : Int {
    return when(type) {
        is ThirtyFiveProductVM -> { 1 }
        is ThirtyFiveBannerVM, is ThirtyFiveBannerListVM,
            is ThirtyFiveCarouselVM, is ThirtyFiveRankingVM -> { defaultColumnCount }
        else -> { 1 }
    }
}