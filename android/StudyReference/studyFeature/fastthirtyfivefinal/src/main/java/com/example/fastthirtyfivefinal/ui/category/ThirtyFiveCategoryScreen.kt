package com.example.fastthirtyfivefinal.ui.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfivefinal.ui.common.ThirtyFiveProductCard
import com.example.fastthirtyfivefinal.viewmodel.category.ThirtyFiveCategoryViewModel

@Composable
fun ThirtyFiveCategoryScreen(
    category: ThirtyFiveCategory,
    viewModel: ThirtyFiveCategoryViewModel = hiltViewModel(),
) {
    val products by viewModel.products.collectAsState()

    /**
     * 주의 : composition 될때 호출되는 부분인데 key 값이 변경된다면 계속해서 호출되는 현상이 있다.
     * 만약 이 screen 이 계속해서 카테고리 변경을 요청하는 경우 ui 가 자주 바뀌므로 주의 할 필요가 있다.
     * */
    LaunchedEffect(key1 = category) {
        viewModel.updateCategory(category)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(products.size) {idx ->
            ThirtyFiveProductCard(viewModel = products[idx]) {
                viewModel.openProduct(it)
            }
        }
    }
}