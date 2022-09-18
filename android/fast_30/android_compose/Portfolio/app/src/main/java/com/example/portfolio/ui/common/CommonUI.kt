package com.example.portfolio.ui.common

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
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