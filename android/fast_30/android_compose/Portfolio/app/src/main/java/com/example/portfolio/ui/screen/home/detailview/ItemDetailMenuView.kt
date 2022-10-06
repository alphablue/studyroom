package com.example.portfolio.ui.screen.home.detailview

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.portfolio.R
import com.example.portfolio.RestaurantMenu
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.ui.common.notification.NotificationBuilder
import com.example.portfolio.ui.theme.backgroundColor

const val DETAIL_MENU_VIEW = "메뉴"

@Composable
fun DetailMenuView() {

    val context = LocalContext.current
    val menuList = remember { mutableStateListOf<RestaurantMenu>() }

    var dialogState by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(true) {
        FirebaseObject.getTestMenus {
            menuList.addAll(it)
        }
    }

    Column {
        menuList.forEachIndexed { index, menuData ->
            Log.d("detail_menu", menuData.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        dialogState = true
                        selectedIndex = index
                    }
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(menuData.image)
                        .error(R.drawable.roadingimage)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Menu Image",
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Text(menuData.menuName ?: "")
                    Text(menuData.detailContent ?: "")
                    Text(menuData.price ?: "")
                }
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(backgroundColor)
            )
        }
    }

    if (dialogState) {
        OrderDialog(
            dialogStateCallBack = { state -> dialogState = state },
            menuName = menuList[selectedIndex].menuName ?: "오류가 발생했습니다."
        ) {
            val notify = NotificationBuilder(context)
            notify.createDeliveryNotificationChannel(
                true,
                "주문완료",
                "주문이 접수 되었습니다."
            )



            dialogState = false
        }
    }
}

@Composable
fun OrderDialog(
    dialogStateCallBack: (Boolean) -> Unit,
    menuName: String,
    confirmEvent: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { dialogStateCallBack(false) },
        confirmButton = {
            Button(onClick = confirmEvent) {
                Text("주문하기")
            }
        },
        dismissButton = {
            Button(onClick = { dialogStateCallBack(false) }) {
                Text("취소")
            }
        },
        text = { Text(" '${menuName}' 주문하기") },
        properties = DialogProperties(dismissOnClickOutside = false)
    )
}