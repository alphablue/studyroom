package com.example.fastthirtyfivefinal.ui.main

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveProductCard
import com.example.fastthirtyfivefinal.viewmodel.MainViewModelOld

@Composable
fun ThirtyFiveMainInsideScreen(
    mainViewModel: MainViewModelOld
) {
    // state를 통해서 viewmodel 을 가져온다?
    val productList by mainViewModel.productList.collectAsState(initial = listOf())

    LazyColumn {
        items(productList.size) {
            ThirtyFiveProductCard(
                product = productList[it]
            ) {

            }
        }
    }
}