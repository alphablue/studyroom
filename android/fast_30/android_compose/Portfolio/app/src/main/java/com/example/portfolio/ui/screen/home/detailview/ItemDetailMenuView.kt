package com.example.portfolio.ui.screen.home.detailview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.localdb.CartWithOrder
import com.example.portfolio.model.uidatamodels.RestaurantMenu
import com.example.portfolio.ui.screen.util.localRoomLikeKey

const val DETAIL_MENU_VIEW = "메뉴"

@Composable
fun DetailMenuView(
    sharedViewModel: MainActivityViewModel,
    restaurantName: String,
    goLogin: () -> Unit
) {

    val context = LocalContext.current
    val menuList = remember { mutableStateListOf<RestaurantMenu>() }

    var dialogState by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }
    val loginState = sharedViewModel.loginState

    LaunchedEffect(true) {
        FirebaseObject.getTestMenus {
            menuList.addAll(it)
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        menuList.forEachIndexed { index, menuData ->
            Log.d("detail_menu", menuData.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        dialogState = true
                        selectedIndex = index
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(menuData.image)
                        .error(R.drawable.roadingimage)
                        .build(),
                    contentDescription = "Menu Image",
                    modifier = Modifier
                        .size(100.dp),
                    contentScale = ContentScale.FillBounds
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(menuData.menuName ?: "")
                    Text(menuData.detailContent ?: "")
                    Text(menuData.price ?: "")
                }
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.White)
            )
        }
    }

    if (dialogState) {

        if (loginState) {
            OrderDialog(
                dialogStateCallBack = { state -> dialogState = state },
                dialogMainContent = menuList[selectedIndex].menuName ?: "오류가 발생했습니다.",
                confirmButtonContent = "장바구니담기"
            ) {

                val userid = sharedViewModel.userInfo?.id ?: "none"
                val restaurantId = menuList[selectedIndex].restaurantId ?: "none"
                val key = localRoomLikeKey(userId = userid, restaurantId)
                val insertObj = CartWithOrder(
                    userId = userid,
                    restaurantId = restaurantId,
                    restaurantName = restaurantName,
                    menuName = menuList[selectedIndex].menuName ?: "none",
                    price = "${ menuList[selectedIndex].price ?: "none"} 원"
                )

                sharedViewModel.insertCart(key, insertObj)

                dialogState = false
            }
        } else {
            OrderDialog(
                dialogStateCallBack = { state -> dialogState = state },
                confirmButtonContent = "로그인하기",
                dialogMainContent = "로그인이 필요합니다.",
                confirmEvent = goLogin
            )
        }
    }
}

@Composable
fun OrderDialog(
    dialogStateCallBack: (Boolean) -> Unit,
    dialogMainContent: String = "",
    confirmButtonContent: String = "승인",
    confirmEvent: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dialogStateCallBack(false) },
        confirmButton = {
            Button(onClick = confirmEvent) {
                Text(confirmButtonContent)
            }
        },
        dismissButton = {
            Button(onClick = { dialogStateCallBack(false) }) {
                Text("취소")
            }
        },
        text = { Text(dialogMainContent) },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}