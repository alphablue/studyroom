package com.example.portfolio.ui.common

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.portfolio.databinding.StarRatingBarBinding
import com.example.portfolio.ui.theme.textColor

@Composable
fun StarRatingBar(
    rateCount: Float,
    modifier: Modifier = Modifier
) {
    AndroidViewBinding(StarRatingBarBinding::inflate, modifier = modifier) {
        ratingBar.rating = rateCount
    }
}

@Composable
fun IconTextButton(
    context: Context,
    activeIcon: ImageVector,
    inactiveIcon: ImageVector,
    text: String,
    toggleEvent: (Boolean) -> Unit,
) {
    val toggleObserver = remember { MutableInteractionSource() }

    IconToggleButton(
        checked = false,
        onCheckedChange = toggleEvent,
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Icon(imageVector = inactiveIcon, contentDescription = "icon")
            Text(text)
        }
    }
}

@Composable
fun SimpleTitleTopBar(
    upPress: () -> Unit,
    title: String,
) {
    TopAppBar(modifier = Modifier
        .statusBarsPadding()
        .fillMaxWidth()) {
        IconButton(onClick = upPress, modifier = Modifier.align(Alignment.Top)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = textColor,
                contentDescription = "back"
            )
        }

        Text(
            title,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}