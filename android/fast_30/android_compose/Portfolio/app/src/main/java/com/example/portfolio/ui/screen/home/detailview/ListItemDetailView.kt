package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.portfolio.MainActivityViewModel

const val detailRout = "detail"

@Composable
fun ListItemDetailView(
    sharedViewModel: MainActivityViewModel
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//        ) {
//            AsyncImage(
//                model = ImageRequest
//                    .Builder(context)
//                    .data(image)
//                    .build(),
//                contentDescription = "restaurantImage",
//                modifier = Modifier.fillMaxSize()
//            )

//            Row(
//                modifier = Modifier
//                    .clip(RoundedCornerShape(12.dp))
//                    .align(Alignment.BottomCenter)
//                    .offset(y = 16.dp)
//            ){}

//        }


        sharedViewModel.detailItem?.let {
            Text("${it.address}, ${it.id}")
        }?: Text(text = "detail View")
    }
}