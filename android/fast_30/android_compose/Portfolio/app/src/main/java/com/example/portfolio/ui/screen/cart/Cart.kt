package com.example.portfolio.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.portfolio.FloatingState
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.screen.util.localRoomLikeKey

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
        Column{
            SimpleTitleTopBar(upPress = upPress, title = "장바구니")

            if(cartKeyItems.isEmpty()) {
                sharedViewModel.floatingState = FloatingState.NONE
                Text(text = "장바구니가 비어있어요")
            } else {
                sharedViewModel.floatingState = FloatingState.ORDER

                LazyColumn {
                    items(cartKeyItems) { cartItem ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text("${cartItem.restaurantName}, ${cartItem.menuName}, ${cartItem.price}")
                            IconButton(onClick = {
                                val key = localRoomLikeKey(cartItem.userId, cartItem.restaurantId)
                                sharedViewModel.deleteCart(key, cartItem)
                            }) {
                                Icon(imageVector = Icons.Filled.Delete, "cartDelete")
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}