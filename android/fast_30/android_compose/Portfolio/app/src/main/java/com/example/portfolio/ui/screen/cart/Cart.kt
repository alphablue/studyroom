package com.example.portfolio.ui.screen.cart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.portfolio.FloatingState
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.localdb.CartWithOrder
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.screen.util.localRoomLikeKey
import com.example.portfolio.ui.theme.secondaryBlue

@Composable
fun Cart(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit
) {
    val userid = sharedViewModel.userInfo?.id ?: "none"
    val cartKeyItems = sharedViewModel.userCartMap.filterKeys { userid in it }
        .map { it.value }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SimpleTitleTopBar(isUpPress = true, upPress = upPress, title = "장바구니")

            if(cartKeyItems.isEmpty()) {
                sharedViewModel.floatingState = FloatingState.NONE
                Text(text = "장바구니가 비어있어요")
            } else {
                sharedViewModel.floatingState = FloatingState.ORDER

                LazyColumn{
                    items(cartKeyItems) { cartItem ->
                        Spacer(modifier = Modifier.height(6.dp))
                        
                        CartCardView(cartItem = cartItem)
                    }
                }
            }
        }
    }
}

@Composable
fun CartCardView(
    cartItem: CartWithOrder,
) {
    val resName = cartItem.restaurantName
    val menuName = cartItem.menuName
    val price = cartItem.price
    
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
                Text(resName)
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