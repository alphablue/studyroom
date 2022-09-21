package com.example.portfolio.ui.screen.home.detailview

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

const val detailRout = "detail"

@Composable
fun ListItemDetailView(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit
) {
    var detailModel by remember { mutableStateOf<NearRestaurantInfo?>(null) }

    LaunchedEffect(key1 = sharedViewModel) {
        detailModel = sharedViewModel.detailItem
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ItemDetailViewTopBar(
                upPress = upPress,
                restaurantName = detailModel?.name ?: "정보를 받을 수 없습니다."
            )
            detailModel?.let { info ->
                DetailTopView(
                    imgUrl = info.imgUri,
                    detailModel = info,
                    modifier = Modifier.fillMaxWidth()
                )

                DetailMiddleView(detailModel = info)
            }
        }
    }
}

@Composable
fun DetailTopView(
    imgUrl: Uri?,
    detailModel: NearRestaurantInfo,
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
                modifier = Modifier
                    .background(
                        lightPrimaryBlue.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                    )
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                StarRatingBar(rateCount = detailModel.rating)

                BadgedBox(badge = { }) {
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
fun DetailMiddleView(
    detailModel: NearRestaurantInfo
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text("배달 시간 : ${detailModel.deliveryTime}")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        Text("배달팁 : ${detailModel.deliveryTip}")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )

        val restaurantLocation = LatLng(detailModel.lat, detailModel.lon)
        val cameraPosition = rememberCameraPositionState() {
            position =
                CameraPosition.fromLatLngZoom(restaurantLocation, 15f)
        }
        val markerPosition =
            rememberMarkerState("restaurantLocation", position = restaurantLocation)


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            GoogleMap(
                Modifier.fillMaxWidth(),
                cameraPositionState = cameraPosition,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ) {
                Marker(
                    state = markerPosition
                )
            }
            Row(
                modifier = Modifier.background(
                    lightPrimaryBlue,
                    shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { },
                    fontSize = 15.sp,
                    text = "주소복사",
                    color = textColor,
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .width(1.dp)
                        .background(color = textColor)
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { },
                    fontSize = 15.sp,
                    text = "지도보기",
                    color = textColor,
                    textAlign = TextAlign.Center
                )
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

@Composable
fun ItemDetailBottomView() {
    var selectedTab by remember { mutableStateOf(0) }
    var selectedTabName by remember { mutableStateOf(DETAIL_MENU_VIEW)}

    val tabContent = mapOf(
        DETAIL_MENU_VIEW to DetailMenuView(),
        DETAIL_REVIEW_VIEW to DetailReviewView()
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val tabItems = listOf(DETAIL_MENU_VIEW, DETAIL_REVIEW_VIEW)

            tabItems.forEachIndexed { index, s ->
                TextButton(
                    onClick = {
                        selectedTab = index
                        selectedTabName = tabItems[index]
                              },
                    colors = ButtonDefaults.buttonColors(backgroundColor = lightPrimaryBlue)
                ) {
                    Text(s, color = textColor, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }

                if (index != tabItems.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(textColor)
                    )
                }
            }
        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))

        tabContent[selectedTabName]
    }

}