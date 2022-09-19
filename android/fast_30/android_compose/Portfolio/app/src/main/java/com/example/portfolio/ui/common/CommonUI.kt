package com.example.portfolio.ui.common

import android.content.Context
import android.graphics.drawable.Icon
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.portfolio.databinding.StarRatingBarBinding

@Composable
fun StarRatingBar(
    rateCount: Float,
) {
    Surface {
        AndroidViewBinding(StarRatingBarBinding::inflate) {
            ratingBar.rating = rateCount
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
    val toggleObserver = remember { MutableInteractionSource()}

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