package com.example.fastthirtyfivefinal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSalesStatus.ON_DISCOUNT
import com.example.fastthirtyfive_domain.model.ThirtyFiveSalesStatus.ON_SALE
import com.example.fastthirtyfive_domain.model.ThirtyFiveSalesStatus.SOLD_OUT
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop
import com.example.fastthirtyfive_domain.model.Top
import com.example.fastthirtyfivefinal.R
import com.example.fastthirtyfivefinal.delegate.ThirtyFiveProductDelegate
import com.example.fastthirtyfivefinal.model.ThirtyFiveProductVM
import com.example.fastthirtyfivefinal.ui.theme.Purple200

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThirtyFiveProductCard(
    navHostController: NavHostController,
    viewModel: ThirtyFiveProductVM,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max) // 하위 compose 를 계산해서 적절한 크기를 맞추는 옵션
            .padding(10.dp)
            .shadow(elevation = 10.dp),
        onClick = { viewModel.openProduct(navHostController, viewModel.model) }
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
                fontWeight = FontWeight.SemiBold,
                text = viewModel.model.shop.shopName
            )
            Text(
                fontSize = 14.sp,
                text = viewModel.model.productName
            )
            Price(viewModel.model)
        }
    }
}

// 할인상품 확인
@Composable
fun Price(
    product: ThirtyFiveProduct
) {
    when (product.price.salesStatus) {
        ON_SALE -> {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                text = "${product.price.originPrice}원"
            )
        }

        ON_DISCOUNT -> {
            Text(
                fontSize = 14.sp,
                style = TextStyle(textDecoration = TextDecoration.LineThrough),
                text = "${product.price.originPrice}원"
            )
            Row {
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    text = "할인가: "
                )
                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple200,
                    text = "${product.price.finalPrice}원"
                )
            }
        }

        SOLD_OUT -> {
            Text(
                fontSize = 18.sp,
                color = Color(0xff666666),
                text = "판매종료"
            )
        }
    }
}

@Composable
@Preview
private fun PreviewProductCard() {
    ThirtyFiveProductCard(
//        navHostController = NavHostController(LocalContext.current),
        rememberNavController(),
        viewModel =
            ThirtyFiveProductVM(
                model = ThirtyFiveProduct(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = ThirtyFivePrice(
                        30000,
                        300000,
                        ON_SALE,
                    ),
                    category = Top,
                    shop = ThirtyFiveShop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false
                ),
                productDelegate = object : ThirtyFiveProductDelegate {
                    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {

                    }
                }
            )
    )
}

@Composable
@Preview
private fun PreviewProductCardDisCount() {
    ThirtyFiveProductCard(
        navHostController = rememberNavController(),
        viewModel =
            ThirtyFiveProductVM(
                model = ThirtyFiveProduct(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = ThirtyFivePrice(
                        30000,
                        20000,
                        ON_DISCOUNT,
                    ),
                    category = Top,
                    shop = ThirtyFiveShop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                ),
                productDelegate = object : ThirtyFiveProductDelegate {
                    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {

                    }

                }
            )
    )
}

@Composable
@Preview
private fun PreviewProductCardSoldOut() {
    ThirtyFiveProductCard(
        navHostController = rememberNavController(),
        viewModel =
            ThirtyFiveProductVM(
                model = ThirtyFiveProduct(
                    productId = "1",
                    productName = "상품 이름",
                    imageUrl = "",
                    price = ThirtyFivePrice(
                        30000,
                        30000,
                        SOLD_OUT,
                    ),
                    category = Top,
                    shop = ThirtyFiveShop(
                        "1",
                        "샵 이름",
                        "",
                    ),
                    isNew = false,
                    isFreeShipping = false,
                ),
                productDelegate = object : ThirtyFiveProductDelegate {
                    override fun openProduct(navHostController: NavHostController, product: ThirtyFiveProduct) {

                    }
                }
            )
    )
}