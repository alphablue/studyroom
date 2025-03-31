package com.example.fastthirtyfive_domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ThirtyFiveAccountInfo(
    val tokenId: String,
    val name: String,
    val type: LoginType
) {
    enum class LoginType {
        GOOGLE
    }
}