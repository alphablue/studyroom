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
import com.example.portfolio.repository.firebasemodule.FirebaseObject
import com.example.portfolio.ui.common.notification.NotificationBuilder
import com.example.portfolio.ui.theme.backgroundColor

const val DETAIL_MENU_VIEW = "메뉴"

@Composable
fun DetailMenuView() {

    val context = LocalContext.current
    val menuList = remember { mutableStateListOf<RestaurantMenu>() }

    var dialogState by remember { mutableStateOf(false) }
    var selectedMenuName by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        FirebaseObject.getTestMenus {
            menuList.addAll(it)
        }
    }

    Column {
        menuList.forEach { detailModel ->
            Log.d("detail_menu", detailModel.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable {
                        dialogState = true
                        selectedMenuName = detailModel.menuName ?: "오류가 생겼습니다."
                    }
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(detailModel.image)
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
                    Text(detailModel.menuName ?: "")
                    Text(detailModel.detailContent ?: "")
                    Text(detailModel.price ?: "")
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
            menuName = selectedMenuName
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