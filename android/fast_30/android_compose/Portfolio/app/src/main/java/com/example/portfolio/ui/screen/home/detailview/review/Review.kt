package com.example.portfolio.ui.screen.home.detailview.review

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.model.uidatamodels.User
import com.example.portfolio.ui.common.BigStarRatingBarIndicator
import com.example.portfolio.ui.common.SimpleTitleTopBar

const val reviewRoute = "review"

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WriteReview(
    goCamera: () -> Unit,
    upPress: () -> Unit,
    getUriOfrPreviousStack:(String, (Uri) -> Unit) -> Unit,
    sharedViewModel: MainActivityViewModel
) {
    var reviewContentValue by remember { mutableStateOf("") }
    var ratingValue by remember { mutableStateOf(0f) }
    var uriState by remember { mutableStateOf<Uri?>(null)}
    var userInfo = sharedViewModel.userInfo ?: User()

    getUriOfrPreviousStack(captureUriKey) { uriState = it }

    Surface {
        Column(
            modifier = Modifier
                .background(color = White)
                .imeNestedScroll()
                .imePadding()
        ) {
            SimpleTitleTopBar(upPress = upPress, title = "리뷰 작성") {
                Text("글쓰기")
            }

            Row(
                modifier = Modifier
                    .border(width = 1.dp, color = Black)
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(60.dp)
                        .clickable { goCamera() },
                    model = uriState ?: R.drawable.ic_photo_camera,
                    contentDescription = "사진 찍기"
                )
                BigStarRatingBarIndicator(
                    modifier = Modifier
                        .height(50.dp)
                        .wrapContentWidth(),
                    changedCallback = {
                        ratingValue = it
                        reviewContentValue = it.toString()
                    }
                )
            }

            TextField(
                value = reviewContentValue,
                onValueChange = { reviewContentValue = it },
                placeholder = { Text(text = "리뷰를 작성해 주세요.") },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = White),
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Black)
            )
        }
    }
}