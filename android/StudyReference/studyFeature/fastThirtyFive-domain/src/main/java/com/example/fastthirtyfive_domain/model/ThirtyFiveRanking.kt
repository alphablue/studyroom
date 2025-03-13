package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName(SERIAL_RANKING)
data class ThirtyFiveRanking(
    val rankingId: String,
    val title: String,
    val productList: List<ThirtyFiveProduct>
): ThirtyFiveBaseModel()
