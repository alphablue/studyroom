package com.example.portfolio.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.portfolio.MainActivityViewModel

@Composable
fun Cart(
    sharedViewModel: MainActivityViewModel,
    modifier: Modifier
) {
    val loginState = sharedViewModel.loginState

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(loginState) {
            val itemList = sharedViewModel.userLikeMap
                .toList().map { it.second }

            if(itemList.isEmpty()){
                Text(text = "비어 있어요")
            } else {
                LazyColumn {
                    items(itemList) { like ->
                        Text(text = like.restaurantName)
                    }
                }
            }
        } else {
            Text(text = "Cart")
        }
    }
}