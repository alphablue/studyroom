package com.example.portfolio.ui.screen.home

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.RatingBar
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.databinding.StarRatingBarBinding
import com.example.portfolio.repository.firebasemodule.FirebaseObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    modifier: Modifier,
    activityViewModel: MainActivityViewModel,
    homeViewModel: HomeViewModel
) {

    var menuChipSelected by remember { mutableStateOf(0) }
    val menuList = HomeTabItems.values()

    LaunchedEffect(true) {
        activityViewModel.getLocation { lastLocation ->

            Log.d("Home screen", lastLocation.toString())
            activityViewModel.reverseGeoCodeCallBack(lastLocation)
            homeViewModel.getPoiData(
                location = lastLocation,
                category = menuList.first().searchPara,
                count = 200
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = activityViewModel.splitAddress)
        ScrollableTabRow(
            selectedTabIndex = menuChipSelected,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 12.dp
        ) {

            menuList.forEachIndexed { index, homeTabItems ->
                FilterChip(
                    onClick = { menuChipSelected = index },
                    colors = ChipDefaults.filterChipColors(
                        selectedBackgroundColor = Color.Green,
                        backgroundColor = Color.LightGray,
                    ),
                    selected = menuChipSelected == index,
                ) {
                    Text(
                        homeTabItems.categoryName,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        PoiItem(
            context = LocalContext.current,
            item = NearRestaurantInfo("test", "test", 0.0, 0.0)
        )
    }
}

@Composable
fun PoiItem(
    context: Context,
    item: NearRestaurantInfo,
    modifier: Modifier = Modifier,
) {
    var defaultUri by remember {
        mutableStateOf<Uri?>(null)
    }

    FirebaseObject.getDefaultUrl {
        defaultUri = it
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {


        Log.d("Home poi item", defaultUri.toString())

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(defaultUri)
                .build(),
            contentDescription = "defaultImage"
        )

        Column {
            Text(item.name)
            StarRatingBar(rateCount = item.rating)
        }

    }
}

@Composable
fun StarRatingBar(
    rateCount: Float
) {
    AndroidViewBinding(StarRatingBarBinding::inflate) {
        ratingBar.rating = rateCount
    }
}

enum class HomeTabItems(val categoryName: String, val searchPara: String) {
    TOTAL("전체", "식당"),
    KOREAFOOD("한식", "한식"),
    KOREASNACK("분식", "분식"),
    DESSERT("카페,디저트", "카페;디저트"),
    JAPANESEFOOD("돈카스,회,일식", "돈카스;회;일식"),
    CHICKEN("치킨", "치킨"),
    PIZZA("피자", "피자"),
    ASSIANFOOD("아시안,양식", "아시안;양식"),
    FASTFOOD("패스트푸드", "패스트푸드"),
}