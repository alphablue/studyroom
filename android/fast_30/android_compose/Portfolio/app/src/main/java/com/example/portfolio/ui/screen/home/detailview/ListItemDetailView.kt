package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.screen.home.NearRestaurantInfo
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
