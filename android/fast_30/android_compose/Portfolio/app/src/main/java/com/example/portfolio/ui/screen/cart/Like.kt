package com.example.portfolio.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.screen.util.localRoomLikeKey

@Composable
fun Like(
    sharedViewModel: MainActivityViewModel,
    modifier: Modifier
) {
    val loginState = sharedViewModel.loginState

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(loginState) {
//            val itemList = sharedViewModel.userLikeMap
//                .toList().map { it.second }
//
//            if(itemList.isEmpty()){
//                Text(text = "비어 있어요")
//            } else {
//                LazyColumn {
//                    items(itemList) { like ->
//                        Text(text = like.restaurantName)
//                    }
//                }
//            }
            val userid = sharedViewModel.userInfo?.id ?: "none"
            val cartKeyItems = sharedViewModel.userCartMap.filterKeys { userid in it }
                .map { it.value }

            if(cartKeyItems.isEmpty()) {
                Text(text = "장바구니가 비어있어요")
            } else {
                LazyColumn {
                    items(cartKeyItems) { cartItem ->
                        Row{
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
        } else {
            Text(text = "Like")
        }
    }
}