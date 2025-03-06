package com.example.fastthirtyfive_data.database.converter

import androidx.room.TypeConverter
import com.example.fastthirtyfive_domain.model.ThirtyFiveCategory
import com.example.fastthirtyfive_domain.model.ThirtyFivePrice
import com.example.fastthirtyfive_domain.model.ThirtyFiveShop
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ThirtyFiveLikeConverter {

    // kotlin serialization 을 사용
    @TypeConverter
    fun fromPrice(value: ThirtyFivePrice): String {
        return Json.encodeToString<ThirtyFivePrice>(value)
    }

    @TypeConverter
    fun toPrice(value: String): ThirtyFivePrice {
        return Json.decodeFromString<ThirtyFivePrice>(value)
    }

    @TypeConverter
    fun fromCategory(value: ThirtyFiveCategory): String {
        return Json.encodeToString<ThirtyFiveCategory>(value)
    }

    @TypeConverter
    fun toCategory(value: String): ThirtyFiveCategory {
        return Json.decodeFromString<ThirtyFiveCategory>(value)
    }

    @TypeConverter
    fun fromShop(value: ThirtyFiveShop): String {
        return Json.encodeToString<ThirtyFiveShop>(value)
    }

    @TypeConverter
    fun toShop(value: String): ThirtyFiveShop {
        return Json.decodeFromString<ThirtyFiveShop>(value)
    }
}