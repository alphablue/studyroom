package com.example.portfolio.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.theme.secondaryBlue

const val orderRoute = "order"

@Composable
fun OrderHistoryView(
    historyViewModel: HistoryViewModel,
    userId: String,
    upPress: () -> Unit,
) {
    val historyData = historyViewModel.allHistoryData

    SideEffect {
        historyViewModel.getAllHistory(userId = userId)
    }

    Surface {

        if (historyData.isEmpty().not()) {
            Column(
                Modifier
                    .background(color = Color.White)
                    .verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleTitleTopBar(isUpPress = true, upPress = { upPress() }, title = "주문목록")

                historyData.forEach {
                    Spacer(modifier = Modifier.height(10.dp))

                    HistoryCard(
                        restaurantName = it.cartWithOrder.restaurantName,
                        date = it.date,
                        menuName = it.cartWithOrder.menuName,
                        price = it.cartWithOrder.price
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("주문 내역이 없습니다.")
            }
        }
    }
}

@Composable
fun HistoryCard(
    restaurantName: String,
    date: String,
    menuName: String,
    price: String
) {
    Card(
        border = BorderStroke(1.dp, color = secondaryBlue),
        modifier = Modifier
            .fillMaxWidth(0.85f),
        backgroundColor = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(restaurantName)
                Text(date)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(menuName)
                Text(price)
            }
        }
    }
}