package com.example.portfolio.ui.common

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import com.example.portfolio.databinding.BigStarRatingBarBinding
import com.example.portfolio.databinding.StarRatingBarBinding
import com.example.portfolio.ui.theme.textColor

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

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
fun BigStarRatingBarIndicator(
    modifier: Modifier = Modifier,
    changedCallback: (Float) -> Unit
) {
    AndroidViewBinding(factory = BigStarRatingBarBinding::inflate, modifier = modifier) {
        bigRatingBar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            changedCallback(fl)
        }
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
    isUpPress: Boolean,
    upPress: () -> Unit = {},
    title: String,
    workingOption: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        ) {
        if(isUpPress){
            IconButton(
                onClick = upPress,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = textColor,
                    contentDescription = "back"
                )
            }
        }

        Text(
            title,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                workingOption()
            }
        }
    }
}