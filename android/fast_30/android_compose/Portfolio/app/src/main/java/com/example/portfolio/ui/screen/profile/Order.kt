package com.example.portfolio.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.example.portfolio.ui.common.SimpleTitleTopBar

const val orderRoute = "order"

@Composable
fun OrderHistoryView(
    historyViewModel: HistoryViewModel,
    userId: String,
    upPress: () -> Unit,
) {
    val historyData = historyViewModel.allHistoryData

    SideEffect{
        historyViewModel.getAllHistory(userId = userId)
    }

    Surface {
        Column {
            SimpleTitleTopBar(upPress = { upPress() }, title = "주문목록")

            historyData.forEach {
                Text(it.cartWithOrder.menuName)
            }
        }
    }
}