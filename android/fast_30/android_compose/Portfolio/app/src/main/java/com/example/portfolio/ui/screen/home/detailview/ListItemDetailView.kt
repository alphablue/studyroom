package com.example.portfolio.ui.screen.home.detailview

import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.localdb.Like
import com.example.portfolio.model.uidatamodels.NearRestaurantInfo
import com.example.portfolio.ui.common.StarRatingBar
import com.example.portfolio.ui.screen.util.findActivity
import com.example.portfolio.ui.screen.util.localRoomLikeKey
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
    upPress: () -> Unit,
    goLogin: () -> Unit,
    goReview: () -> Unit
) {
    val detailModel = sharedViewModel.detailItem
    val restaurantName = detailModel?.name ?: "정보를 받을 수 없습니다."

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
                    restaurantName = restaurantName
                )

                detailModel?.let { info ->
                    DetailTopView(
                        imgUrl = info.imgUri,
                        detailModel = info,
                        sharedViewModel = sharedViewModel,
                        goLogin = goLogin,
                        modifier = Modifier.fillMaxWidth()
                    )

                    DetailMiddleView(detailModel = info)
                    DetailBottomView(sharedViewModel, restaurantName, goLogin, goReview)
                }
            }
        }
    }

@Composable
fun DetailTopView(
    imgUrl: Uri?,
    detailModel: NearRestaurantInfo,
    sharedViewModel: MainActivityViewModel,
    goLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val loginState = sharedViewModel.loginState
    var likeState by remember{ mutableStateOf(false)}

    if(loginState) {
        sharedViewModel.userInfo?.let {
            val localKey = localRoomLikeKey(it.id, detailModel.id)
            likeState = sharedViewModel.userLikeMap.containsKey(localKey)
        }
    }

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
                        putExtra(Intent.EXTRA_TEXT, "가게 위치 정보 확인\n ${detailModel.address}")
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
                TextButton(onClick = {
                    if(loginState.not()) {
                        goLogin()
                    } else {

                        sharedViewModel.userInfo?.let {
                            val localKey = localRoomLikeKey(it.id, detailModel.id)
                            val queryObj = Like(
                                userId = it.id,
                                restaurantId = detailModel.id,
                                restaurantImage = detailModel.imgUri.toString(),
                                restaurantName = detailModel.name
                            )

                            likeState = if(likeState) {
                                sharedViewModel.deleteLike(localKey, queryObj)
                                false
                            } else {
                                sharedViewModel.insertLike(
                                    key = localKey,
                                    queryObj
                                )
                                true
                            }

                        }
                    }
                }) {
                    if(likeState.not())
                        Text("찜", fontSize = 18.sp, color = textColor)
                    else
                        Text("찜 해제", fontSize = 18.sp, color = textColor)
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
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

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
                modifier = Modifier
                    .background(
                        lightPrimaryBlue,
                        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    )
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .clickable
                        {
                            clipboardManager.setText(AnnotatedString("${detailModel.address}\n${detailModel.name}"))
                            Toast
                                .makeText(context, "복사되었습니다.", Toast.LENGTH_SHORT)
                                .show()
                        },
                ){
                    Text(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        fontSize = 15.sp,
                        text = "주소복사",
                        color = textColor,
                        textAlign = TextAlign.Center,
                    )
                }
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
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
    ) {

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
fun DetailBottomView(
    sharedViewModel: MainActivityViewModel,
    restaurantName: String,
    goLogin: () -> Unit,
    goReview: () -> Unit,
) {
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
                    0-> DetailMenuView(
                        sharedViewModel,
                        restaurantName,
                        goLogin
                    )
                    1-> DetailReviewView(
                        goReview
                    )
                    else -> {}
                }
        }
    }
}