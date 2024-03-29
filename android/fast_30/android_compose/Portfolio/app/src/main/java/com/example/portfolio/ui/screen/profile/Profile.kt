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
import com.example.portfolio.ui.common.SimpleTitleTopBar
import com.example.portfolio.ui.theme.textSecondaryColor

@Composable
fun Profile(
    sharedViewModel: MainActivityViewModel,
    goLogin: () -> Unit,
    goOrder: () -> Unit
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
            SimpleTitleTopBar(isUpPress = false, title = "나의 프로필")

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
                            userImg = it.profileImage,
                            userName = it.name,
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
                if (loginState) {
                    SettingLayout(onClick = { goOrder() }, name = "주문목록")
                    SettingLayout(onClick = { sharedViewModel.signOut() }, name = "로그아웃")
                    SettingLayout(onClick = { sharedViewModel.userWithdrawal() }, name = "회원탈퇴")
                }
            }
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
        Box(
            Modifier
                .weight(1f)
                .fillMaxSize()
                .clickable(onClick = onClick),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = userName,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SettingLayout(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    name: String
) {
    Row(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            name,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 18.sp,
            color = textSecondaryColor
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "arrowRight",
            modifier = Modifier
                .size(24.dp)
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}