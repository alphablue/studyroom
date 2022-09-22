package com.example.portfolio.ui.screen.home.detailview

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.portfolio.R
import com.example.portfolio.ui.theme.backgroundColor

const val DETAIL_MENU_VIEW = "메뉴"

@Composable
fun DetailMenuView() {
    val dumpItems = listOf(
        RestaurantMenu(null, "test1", "testDetail1", "price1"),
        RestaurantMenu(null, "test2", "testDetail2", "price2"),
        RestaurantMenu(null, "test3", "testDetail3", "price3")
    )

    val context = LocalContext.current

    Column{

        dumpItems.forEach { detailModel ->
            Row(modifier = Modifier.fillMaxWidth().height(300.dp)){
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(detailModel.imgUri)
                        .error(R.drawable.roadingimage)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Menu Image",
                    modifier = Modifier.weight(0.25f).fillMaxSize()
                )
                Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    Text(detailModel.menuName)
                    Text(detailModel.menuDetailContent)
                    Text(detailModel.menuPrice)
                }
            }
            Spacer(Modifier.fillMaxWidth().height(1.dp).background(backgroundColor))
        }
    }
}

data class RestaurantMenu(
    val imgUri: Uri? = null,
    val menuName: String,
    val menuDetailContent: String,
    val menuPrice: String
)