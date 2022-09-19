package com.example.portfolio.ui.screen.home.detailview

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ListItemDetailView(
    image: Uri,
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(image)
                    .build(),
                contentDescription = "restaurantImage",
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.BottomCenter)
                    .offset(y = 16.dp)
            ){

            }

        }
    }
}