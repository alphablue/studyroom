package com.example.fastthirtyfivefinal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveRanking
import com.example.fastthirtyfivefinal.R

private const val DEFAULT_RANKING_ITEM_COUNT = 3

@Composable
fun ThirtyFiveRankingCard(
    model: ThirtyFiveRanking,
    onClick: (ThirtyFiveProduct) -> Unit
) {
    val pagerState = rememberPagerState { model.productList.size / DEFAULT_RANKING_ITEM_COUNT }

    Column {
        Text(
            text = model.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        )
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 50.dp)
    ) { idx ->

        Column {
            RankingProductCard(idx * 3, model.productList[idx * 3], onClick)
            RankingProductCard(idx * 3 + 1, model.productList[idx * 3 + 1], onClick)
            RankingProductCard(idx * 3 + 2, model.productList[idx * 3 + 2], onClick)
        }
    }
}

@Composable
private fun RankingProductCard(
    index: Int,
    product: ThirtyFiveProduct,
    onClick: (ThirtyFiveProduct) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "${index + 1}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(0.dp, 0.dp, 10.dp, 0.dp)
        )
        Image(
            painter = painterResource(R.drawable.thirty_five_product_image),
            contentDescription = "product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(80.dp)
                .aspectRatio(0.7f) // 1보다 크면 가로가 크고 1보다 작으면 세로가 길어지는 것
        )
        Column(
            modifier = Modifier
                .padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Text(
                fontSize = 14.sp,
                text = product.shop.shopName,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            Text(
                fontSize = 14.sp,
                text = product.productName,
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
            )

            Price(product = product)
        }
    }
}