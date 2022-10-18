package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.model.uidatamodels.DisPlayReview
import com.example.portfolio.ui.common.StarRatingBar
import com.example.portfolio.ui.theme.secondaryBlue

const val DETAIL_REVIEW_VIEW = "리뷰"

@Composable
fun DetailReviewView(
    selectedResId: String,
    goReview: () -> Unit,
    loginState: Boolean,
    callBackDialogState: (Boolean) -> Unit
) {
    val getReviewData = remember { mutableStateListOf<DisPlayReview>() }
    val getResReviews = remember { mutableStateListOf<DisPlayReview>()}

    LaunchedEffect(true) {
        FirebaseObject.getTestReview {
            getReviewData.addAll(it)
        }

        FirebaseObject.getReviewWithResId(selectedResId) {
            getResReviews.addAll(it)
        }
    }

    Column(
        modifier = Modifier.background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(30.dp)
                .border(width = 1.dp, color = secondaryBlue)
                .clickable {
                    if (loginState) {
                        goReview()
                    } else {
                        callBackDialogState(true)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.DriveFileRenameOutline,
                contentDescription = "writeReview"
            )
            Text(text = "리뷰쓰기")
        }

        Spacer(modifier = Modifier.height(4.dp))

        for((reviewInfo, userInfo) in getResReviews) {
            DrawReview(
                userNickName = userInfo.name,
                userProfileImg = userInfo.profileImage,
                ratingValue = reviewInfo.rating.toFloat(),
                reviewDate = reviewInfo.date,
                takePictureData = reviewInfo.takePicture,
                contentText = reviewInfo.content
            )
        }

        for ((reviewInfo, userInfo) in getReviewData) {
            DrawReview(
                userNickName = userInfo.name,
                userProfileImg = userInfo.profileImage,
                ratingValue = reviewInfo.rating.toFloat(),
                reviewDate = reviewInfo.date,
                takePictureData = reviewInfo.takePicture,
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
                .padding(horizontal = 6.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userProfileImg)
                    .build(),
                contentDescription = "userProfileImage",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(100)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = userNickName)
                StarRatingBar(rateCount = ratingValue)
            }
            Text(text = reviewDate)
        }
        Spacer(modifier = Modifier.height(10.dp))

        if (takePictureData.isNullOrEmpty().not()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(150.dp),
                    model = takePictureData,
                    contentDescription = "contextImage",
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(Modifier.height(10.dp))
        }

        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(text = contentText)
        }

    }

    Spacer(modifier = Modifier.height(10.dp))
}