package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(SERIAL_BANNER_LIST)
data class ThirtyFiveBannerList(
    val bannerId: String,
    val imageList: List<String>,
) : ThirtyFiveBaseModel()