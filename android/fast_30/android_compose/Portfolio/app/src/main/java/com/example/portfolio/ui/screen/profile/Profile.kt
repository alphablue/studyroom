package com.example.portfolio.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.R
import com.example.portfolio.ui.theme.textSecondaryColor

@Composable
fun Profile(
    sharedViewModel: MainActivityViewModel,
    goLogin: () -> Unit
) {
    val context = LocalContext.current
    val loginState = sharedViewModel.loginState
    val defaultProfile =
        "android.resource://${context.packageName}/${R.drawable.ic_emptyprofile}"

    val userInfo = sharedViewModel.userInfo

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.Transparent
                )
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                if (loginState) {
                    userInfo?.let {
                        UserBox(
                            modifier = Modifier
                                .height(80.dp),
                            userImg = it.profileImage!!,
                            userName = it.name!!,
                            onClick = {}
                        )
                    }
                } else {
                    UserBox(
                        modifier = Modifier
                            .height(80.dp),
                        userImg = defaultProfile,
                        userName = "로그인이 필요합니다.",
                        onClick = goLogin
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .wrapContentSize()
                    .fillMaxWidth()
            ) {
                SettingLayout(onClick = { /*TODO*/ }, name = "알림설정")
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun UserBox(
    modifier: Modifier = Modifier,
    userImg: String,
    userName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .weight(0.35f)
                .fillMaxSize(),
            model = userImg,
            contentDescription = "userProfileImage"
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(12.dp)
        )
        Text(
            text = userName,
            modifier = Modifier.weight(1f)
                .clickable(onClick = onClick),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SettingLayout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    name: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            name,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clickable(true, onClick = onClick),
            fontSize = 18.sp,
            color = textSecondaryColor
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "arrowRight",
            modifier = Modifier
                .size(24.dp)
                .clickable(true, onClick = onClick)
        )
    }
}