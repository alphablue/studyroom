package com.example.portfolio.ui.screen.home.detailview.review

import android.net.Uri
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.model.uidatamodels.GetReview
import com.example.portfolio.model.uidatamodels.User
import com.example.portfolio.ui.common.BigStarRatingBarIndicator
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.screen.util.getDate
import com.example.portfolio.ui.theme.dividerColor
import com.example.portfolio.ui.theme.gray
import com.example.portfolio.ui.theme.lightPrimaryBlue
import kotlinx.coroutines.launch

const val reviewRoute = "review"

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun WriteReview(
    resId: String,
    goCamera: () -> Unit,
    upPress: () -> Unit,
    getUriOfrPreviousStack: (String, (Uri) -> Unit) -> Unit,
    sharedViewModel: MainActivityViewModel
) {
    var reviewContentValue by remember { mutableStateOf("") }
    var ratingValue by remember { mutableStateOf(0f) }
    var uriState by remember { mutableStateOf<Uri?>(null) }
    val userInfo = sharedViewModel.userInfo ?: User()
    val context = LocalContext.current

    var progressState by remember { mutableStateOf(false) }
    val bringIntoViewRequester = remember { BringIntoViewRequester()}

    val scope = rememberCoroutineScope()
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            scope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }

    fun writeFun() = FirebaseObject.addUserReview(
        restaurantId = resId,
        fileUri = uriState,
        userId = userInfo.id,
        getReview = GetReview(
            rating = ratingValue.toString(),
            content = reviewContentValue,
            date = getDate(),
            userId = userInfo.id,
            restaurantId = resId
        ),
        completeCallback = {
            progressState = false
            upPress()
        },
        failCallBack = {
            progressState = false
            Toast.makeText(context, "업로드에 실패 하였습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show()
        }
    )

    getUriOfrPreviousStack(captureUriKey) { uriState = it }

    Surface {

        Column(
            modifier = Modifier.background(color = White)
        ) {
            SimpleTitleTopBar(upPress = upPress, title = "리뷰 작성") {
                Text(text = "글쓰기", modifier = Modifier.clickable {
                    progressState = true
                    writeFun()
                })
            }
            LazyColumn(
                modifier = Modifier
                    .imeNestedScroll()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                item{

                    Row(
                        modifier = Modifier
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
                            }
                        )
                    }

                    Spacer(modifier = Modifier
                        .height(3.dp)
                        .background(color = dividerColor)
                        .fillMaxWidth())

                    TextField(
                        value = reviewContentValue,
                        onValueChange = { reviewContentValue = it },
                        placeholder = { Text(text = "리뷰를 작성해 주세요.") },
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = White),
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            progressState = true
                            writeFun()
                        },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(0.8f)
                            .bringIntoViewRequester(bringIntoViewRequester = bringIntoViewRequester)
                    ) {
                        Text("작성하기")
                    }
                }
            }
        }
    }

    if (progressState) {
        BackHandler(enabled = true) {}

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = gray.copy(alpha = 0.8f))
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = lightPrimaryBlue,
                strokeWidth = 3.dp
            )
        }
    } else {
        BackHandler(enabled = false) {}
    }
}