package com.example.portfolio.ui.screen.home.detailview

import android.content.Intent
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
import com.example.portfolio.ui.screen.util.findActivity
import com.example.portfolio.ui.theme.lightPrimaryBlue
import com.example.portfolio.ui.theme.textColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

const val detailRout = "detail"

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListItemDetailView(
    sharedViewModel: MainActivityViewModel,
    upPress: () -> Unit
) {
    var detailModel by remember { mutableStateOf<NearRestaurantInfo?>(null) }
    var restaurantAddress = ""

    LaunchedEffect(true) {
        detailModel = sharedViewModel.detailItem

        detailModel?.let {
            sharedViewModel.getReverseGeoCode(
                lat = it.lat, lng = it.lon
            ) { geoCode ->
                restaurantAddress = sharedViewModel.googleGeoCodeConvert(geoCode)
            }
        }
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
                    DetailBottomView()
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
    val activity = context.findActivity()

    Box(
        modifier = modifier
            .height(180.dp)
    ) {

        val rowHeight = 15.dp

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            model = ImageRequest
                .Builder(context)
                .data(imgUrl)
                .error(R.drawable.roadingimage)
                .scale(Scale.FILL)
                .build(),
            contentDescription = "restaurant Detail Main Image",

            )

        Column(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxWidth(0.55f)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = lightPrimaryBlue.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = {
                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1343-4646"))
                    activity.startActivity(callIntent)
                }) {
                    Text("전화", fontSize = 18.sp, color = textColor)
                }
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, height = rowHeight)
                        .background(color = textColor)
                )
                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT, "소제목")
                        putExtra(Intent.EXTRA_TEXT, "주요내용")
                    }

                    val sendIntent = Intent.createChooser(intent, "제목")

                    activity.startActivity(sendIntent)
                }) {
                    Text("공유", fontSize = 18.sp, color = textColor)
                }
                Spacer(
                    modifier = Modifier
                        .size(width = 1.dp, height = rowHeight)
                        .background(color = textColor)
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text("찜", fontSize = 18.sp, color = textColor)
                }
            }
            Row(
                modifier = Modifier
                    .background(
                        lightPrimaryBlue.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp)
                    )
                    .fillMaxWidth()
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StarRatingBar(rateCount = detailModel.rating)

                BadgedBox(badge = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Message,
                        contentDescription = "review icon",
                        tint = textColor
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
        val cameraPosition = rememberCameraPositionState {
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
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
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
                )
                    .height(48.dp),
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

@ExperimentalPagerApi
@Composable
fun DetailBottomView() {
    val tabItems = listOf(DETAIL_MENU_VIEW, DETAIL_REVIEW_VIEW)

    val viewPagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = viewPagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState = viewPagerState,
                        tabPositions = tabPositions
                    )
                )
            }
        ) {

            tabItems.forEachIndexed { index, s ->
                Tab(
                    text = { Text(s) },
                    selected = viewPagerState.currentPage == index,
                    onClick = { coroutineScope.launch { viewPagerState.animateScrollToPage(index) } },
                )
            }
        }
        HorizontalPager(
            count = tabItems.size,
            state = viewPagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
                when(page) {
                    0-> DetailMenuView()
                    1-> DetailReviewView()
                    else -> {}
                }
        }
    }
}