package com.example.portfolio.ui.screen.home.detailview

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Message
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.ui.common.StarRatingBar
import com.example.portfolio.ui.screen.home.NearRestaurantInfo
import com.example.portfolio.ui.theme.lightPrimaryBlue
import com.example.portfolio.ui.theme.textColor

const val detailRout = "detail"

@Composable
fun ListItemDetailView(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit
) {
    var item by remember { mutableStateOf<NearRestaurantInfo?>(null) }

    LaunchedEffect(key1 = sharedViewModel) {
        item = sharedViewModel.detailItem
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ItemDetailViewTopBar(upPress = upPress, restaurantName = item?.name ?: "정보를 받을 수 없습니다.")
            item?.let { info ->
                DetailTopView(
                    imgUrl = info.imgUri,
                    item = info,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DetailTopView(
    imgUrl: Uri?,
    item: NearRestaurantInfo,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .height(180.dp)
    ) {

        val rowHeight = 15.dp

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            model = ImageRequest
                .Builder(context)
                .scale(Scale.FILL)
                .data(imgUrl)
                .error(R.drawable.roadingimage)
                .build(),
            contentDescription = "restaurant Detail Main Image",

        )

        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = lightPrimaryBlue.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text("전화", fontSize = 15.sp, color = textColor)
                }
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, height = rowHeight)
                        .background(color = textColor)
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text("공유", fontSize = 15.sp, color = textColor)
                }
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, height = rowHeight)
                        .background(color = textColor)
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text("찜", fontSize = 15.sp, color = textColor)
                }
            }
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                StarRatingBar(rateCount = item.rating)

                BadgedBox(badge = {  }) {
                    Icon(
                        imageVector = Icons.Outlined.Message,
                        contentDescription = "review icon"
                    )
                }
            }
        }
    }
}

@Composable
fun ItemDetailViewTopBar(
    upPress: () -> Unit,
    restaurantName: String
) {
    TopAppBar(modifier = Modifier.statusBarsPadding()) {
        IconButton(onClick = upPress, modifier = Modifier.align(Alignment.Top)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = textColor,
                contentDescription = "back"
            )
        }

        Text(
            restaurantName,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}
