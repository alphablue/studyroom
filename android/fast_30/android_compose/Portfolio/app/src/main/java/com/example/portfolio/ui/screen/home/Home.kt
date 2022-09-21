package com.example.portfolio.ui.screen.home

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.repository.firebasemodule.FirebaseObject
import com.example.portfolio.ui.screen.util.number2Digits
import com.example.portfolio.ui.theme.gray
import com.example.portfolio.ui.theme.textColor
import com.example.portfolio.ui.theme.textPrimaryColor
import com.example.portfolio.ui.theme.yellow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home(
    modifier: Modifier,
    itemSelect: (NearRestaurantInfo) -> Unit,
    goMap: () -> Unit,
    activityViewModel: MainActivityViewModel,
    homeViewModel: HomeViewModel
) {

    var menuChipSelected by remember { mutableStateOf(0) }
    val menuList = HomeTabItems.values()

    var defaultUri by remember {
        mutableStateOf<Uri?>(null)
    }

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

        FirebaseObject.getDefaultUrl {
            defaultUri = it
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MainAppBar(
            userAddress = activityViewModel.splitAddress,
            goMap = goMap
        )
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(homeViewModel.poiList) { poiItem ->
                PoiItem(
                    context = LocalContext.current,
                    item = poiItem,
                    defaultUri = defaultUri,
                    itemSelect = { itemSelect(poiItem) }
                )
            }
        }

    }
}

@Composable
fun PoiItem(
    context: Context,
    item: NearRestaurantInfo,
    modifier: Modifier = Modifier,
    defaultUri: Uri?,
    itemSelect: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = itemSelect)
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

            PoiDetailItem(
                imageVector = Icons.Filled.Star,
                description = "rating",
                contentText = item.rating.number2Digits(),
                tint = yellow
            )

            PoiDetailItem(
                imageVector = Icons.Outlined.Timer,
                description = "deliveryTimer",
                contentText = item.deliveryTime
            )

            Text(text = item.deliveryTip)
        }
    }
    Spacer(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(gray)
    )
}

@Composable
fun PoiDetailItem(
    imageVector: ImageVector,
    description: String,
    contentText: String,
    tint: Color? = null
) {
    Row {
        tint?.let {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                tint = it
            )
        } ?: Icon(
            imageVector = imageVector,
            contentDescription = description
        )
        Text(text = contentText)
    }
}

@Composable
fun MainAppBar(
    userAddress: String,
    goMap: () -> Unit
) {
    TopAppBar(modifier = Modifier.statusBarsPadding()) {
        Spacer(modifier = Modifier.weight(0.25f))

        Row(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = goMap)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                userAddress,
                color = textColor,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                tint = textColor,
                contentDescription = "new position button"
            )
        }

        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                tint = textPrimaryColor,
                contentDescription = "your shopping cart"
            )
        }
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