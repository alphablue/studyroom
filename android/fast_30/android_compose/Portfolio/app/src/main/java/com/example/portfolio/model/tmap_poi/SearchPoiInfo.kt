package com.example.portfolio.model.tmap_poi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchPoiInfo(
    val count: Int,
    val page: Int,
    val pois: Pois,
    val totalCount: Int
): Parcelable