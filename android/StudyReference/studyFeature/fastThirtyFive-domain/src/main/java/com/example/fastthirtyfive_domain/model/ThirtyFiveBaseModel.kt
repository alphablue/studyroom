package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.Serializable

@Serializable
abstract class ThirtyFiveBaseModel {
}

const val SERIAL_PRODUCT = "PRODUCT"
const val SERIAL_BANNER = "BANNER"
const val SERIAL_BANNER_LIST = "BANNER_LIST"
const val SERIAL_CAROUSEL = "CAROUSEL"
const val SERIAL_RANKING = "RANKING"
