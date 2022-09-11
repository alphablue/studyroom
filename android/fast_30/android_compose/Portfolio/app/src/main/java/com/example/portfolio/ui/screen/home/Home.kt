package com.example.portfolio.ui.screen.home

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.portfolio.viewmodel.MainActivityViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    modifier: Modifier,
    activityViewModel: MainActivityViewModel
) {

    var menuChipSelected by remember{ mutableStateOf(0)}
    activityViewModel.getAddress()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = activityViewModel.splitAddress)
        ScrollableTabRow(selectedTabIndex = menuChipSelected) {
            val menuList = HomeTabItems.values()

            menuList.forEachIndexed { index, homeTabItems ->
                Chip(onClick = { menuChipSelected = index }) {
                    Text(homeTabItems.categoryName)
                }
            }

        }
    }
}

enum class HomeTabItems(val categoryName: String, val searchPara: String){
    TOTAL("전체","전체"),
    KOREAFOOD("한식","한식"),
    KOREASNACK("분식","분식"),
    DESSERT("카페,디저트","카페;디저트"),
    JAPANESEFOOD("돈카스,회,일식","돈카스;회;일식"),
    CHICKEN("치킨","치킨"),
    PIZZA("피자","피자"),
    ASSIANFOOD("아시안,양식","아시안;양식"),
    FASTFOOD("패스트푸드","패스트푸드"),
}