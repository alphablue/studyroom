package com.example.portfolio.model.tmap_poi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class EvChargers(
    val evCharger: @RawValue List<Any>
): Parcelable