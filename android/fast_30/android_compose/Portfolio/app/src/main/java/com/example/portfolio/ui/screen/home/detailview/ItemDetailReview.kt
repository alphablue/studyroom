package com.example.portfolio.ui.screen.home.detailview

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.model.uidatamodels.Review

const val DETAIL_REVIEW_VIEW = "리뷰"

@Composable
fun DetailReviewView() {
    val reviewData = remember { mutableListOf<Review>()}

    LaunchedEffect(true) {
        FirebaseObject.getTestReview {
            reviewData.addAll(it)
        }
    }

    Text("review")
}