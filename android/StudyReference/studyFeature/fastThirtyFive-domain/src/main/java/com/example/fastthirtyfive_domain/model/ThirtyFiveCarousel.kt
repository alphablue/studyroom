package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(SERIAL_CAROUSEL)
data class ThirtyFiveCarousel(
    val carouselId: String,
    val title: String,
    val productList: List<ThirtyFiveProduct>
): ThirtyFiveBaseModel()