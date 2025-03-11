package com.example.fastthirtyfivefinal.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fastthirtyfive_domain.model.ThirtyFiveBanner
import com.example.fastthirtyfive_domain.model.ThirtyFiveBaseModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveBannerCard
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
                is ThirtyFiveProduct -> {
                    ThirtyFiveProductCard(product = item) {

                    }
                }
                is ThirtyFiveBanner -> {
                    ThirtyFiveBannerCard(model = item)
                }
            }
        }
    }
}

private fun getSpanCountByTypes(
    type: ThirtyFiveBaseModel,
    defaultColumnCount: Int
) : Int {
    return when(type) {
        is ThirtyFiveProduct -> { 1 }
        is ThirtyFiveBanner -> { defaultColumnCount }
        else -> { 1 }
    }
}