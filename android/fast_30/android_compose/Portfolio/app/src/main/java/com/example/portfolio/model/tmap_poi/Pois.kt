package com.example.portfolio.model.tmap_poi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pois(
    val poi: List<Poi>
): Parcelable