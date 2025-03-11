package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(SERIAL_BANNER)
data class ThirtyFiveBanner(
    val bannerId: String,
    val imageUrl: String
) : ThirtyFiveBaseModel()