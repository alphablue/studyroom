package com.example.fastthirtyfivefinal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.model.ThirtyFiveBannerVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirtyFiveBannerCard(
    viewModel: ThirtyFiveBannerVM,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(20.dp),
        onClick = { viewModel.openBanner(viewModel.model.bannerId) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.thirty_five_product_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            contentDescription = "product banner"
        )
    }
}