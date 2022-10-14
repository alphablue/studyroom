package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.model.uidatamodels.DisPlayReview
import com.example.portfolio.ui.common.StarRatingBar

const val DETAIL_REVIEW_VIEW = "리뷰"

@Composable
fun DetailReviewView(
    goReview: () -> Unit
) {
    val getReviewData = remember { mutableStateListOf<DisPlayReview>() }

    LaunchedEffect(true) {
        FirebaseObject.getTestReview {
            getReviewData.addAll(it)
        }
    }

    Column {
        Row(
            modifier = Modifier.clickable {
                goReview()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.DriveFileRenameOutline,
                contentDescription = "writeReview"
            )
            Text(text = "리뷰쓰기")
        }

        for ((reviewInfo, userInfo) in getReviewData) {
            DrawReview(
                userNickName = userInfo.name,
                userProfileImg = userInfo.profileImage,
                ratingValue = reviewInfo.rating.toFloat(),
                reviewDate = reviewInfo.date,
                contentText = reviewInfo.content
            )
        }
    }
}

@Composable
fun DrawReview(
    userNickName: String,
    userProfileImg: String,
    ratingValue: Float,
    reviewDate: String,
    takePictureData: String? = null,
    contentText: String,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.wrapContentHeight()
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userProfileImg)
                    .build(),
                contentDescription = "userProfileImage",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(100)),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = userNickName)
                StarRatingBar(rateCount = ratingValue)
            }
            Text(text = reviewDate)
        }
        if (takePictureData != null) {
            AsyncImage(model = takePictureData, contentDescription = "contextImage")
        }
        Text(text = contentText)
    }
}