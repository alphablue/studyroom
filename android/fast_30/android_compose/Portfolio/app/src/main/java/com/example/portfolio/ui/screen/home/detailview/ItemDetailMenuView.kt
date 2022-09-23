package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.portfolio.R
import com.example.portfolio.RestaurantMenu
import com.example.portfolio.repository.firebasemodule.FirebaseObject
import com.example.portfolio.ui.theme.backgroundColor

const val DETAIL_MENU_VIEW = "메뉴"

@Composable
fun DetailMenuView() {

    val context = LocalContext.current
    val menuList = remember{ mutableListOf<RestaurantMenu>()}

    LaunchedEffect(true) {
        FirebaseObject.getTestMenus {
            menuList.addAll(it)
        }
    }

    Column{

        menuList.forEach { detailModel ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)){
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(detailModel.imageUri)
                        .error(R.drawable.roadingimage)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Menu Image",
                    modifier = Modifier
                        .weight(0.25f)
                        .fillMaxSize()
                )
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    Text(detailModel.menuName)
                    Text(detailModel.detailContent)
                    Text(detailModel.price)
                }
            }
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(backgroundColor))
        }
    }
}
