package com.example.fastthirtyfivefinal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fastthirtyfive_domain.model.ThirtyFiveBannerList
import com.example.fastthirtyfivefinal.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirtyFiveBannerListCard(
    model: ThirtyFiveBannerList,
    onClick: (ThirtyFiveBannerList) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { model.imageList.size })

    // auto scroll 을 할 때 카드의 라이프 사이클과 뷰의 라이프 사이클의 동기화 하는 스코프이다?
    // 뷰가 보일 때만 작동하도록 보장 해 준다.
    LaunchedEffect(key1 = pagerState) {
        autoScrollInfinity(pagerState)
    }

    HorizontalPager(state = pagerState) { pageIdx ->
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .shadow(20.dp),
            onClick = { onClick(model) }
        ) {
            Image(
                painter = painterResource(id = R.drawable.thirty_five_product_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                contentDescription = "product banner"
            )
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("pagerNumber $pageIdx")
            }
        }
    }
}

private suspend fun autoScrollInfinity(
    pagerState: PagerState
) {
    while(true) {
        delay(3000)
        pagerState.animateScrollToPage(
            if(pagerState.currentPage != 0 && pagerState.currentPage % pagerState.pageCount == 0) 0
            else pagerState.currentPage + 1
        )
    }
}

