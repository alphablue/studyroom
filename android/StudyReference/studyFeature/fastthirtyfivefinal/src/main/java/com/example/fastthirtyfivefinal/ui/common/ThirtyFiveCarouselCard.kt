package com.example.fastthirtyfivefinal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.model.ThirtyFiveCarouselVM

@Composable
fun ThirtyFiveCarouselCard(
    navHostController: NavHostController,
    viewModel: ThirtyFiveCarouselVM
) {
    val scrollState = rememberLazyListState()

    Column {
        Text(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            text = viewModel.model.title,
            modifier = Modifier.padding(10.dp),
        )

        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(viewModel.model.productList.size) {idx ->
                ThirtyFiveCarouselProductCard(
                    product = viewModel.model.productList[idx],
                    onClick = { viewModel.openCarouselProduct(navHostController, viewModel.model.productList[idx]) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ThirtyFiveCarouselProductCard(
    product: ThirtyFiveProduct,
    onClick: (ThirtyFiveProduct) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(150.dp)
            .wrapContentHeight()
            .padding(10.dp),
        onClick = { onClick(product) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.thirty_five_product_image),
                contentDescription = "product_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // 이미지의 비율에 맞춰서 들어감
            )

            Text(
                fontSize = 14.sp,
                text = product.productName
            )
            Price(product)
        }
    }
}