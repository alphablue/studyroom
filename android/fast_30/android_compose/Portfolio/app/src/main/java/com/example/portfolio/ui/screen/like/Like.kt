package com.example.portfolio.ui.screen.like

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.screen.util.localRoomLikeKey

@Composable
fun Like(
    sharedViewModel: MainActivityViewModel,
    modifier: Modifier
) {
    val loginState = sharedViewModel.loginState

    Column {
        SimpleTitleTopBar(isUpPress = false, title = "나의 찜 목록")

        Column(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(loginState) {
                val userid = sharedViewModel.userInfo?.id ?: "none"
                val itemList = sharedViewModel.userLikeMap
                    .toList().map { it.second }

                if(itemList.isEmpty()){
                    Text(text = "찜 목록이 비어 있어요")
                } else {
                    LazyColumn {
                        items(itemList) { like ->
                            Row(
                                modifier = Modifier.fillMaxWidth(0.9f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(text = like.restaurantName)
                                IconButton(onClick = {
                                    val key = localRoomLikeKey(userid, like.restaurantId)
                                    sharedViewModel.deleteLike(key, like)
                                }) {
                                    Icon(imageVector = Icons.Outlined.DeleteOutline, contentDescription = "like delete")
                                }
                            }
                            Spacer(Modifier.height(4.dp))
                        }
                    }
                }
            }
        }
    }
}