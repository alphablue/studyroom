package com.example.fastthirtyfive_data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword

@Entity(tableName = "thirty_five_search")
data class ThirtyFiveSearchKeywordEntity(
    @PrimaryKey
    val keyword: String
)

fun ThirtyFiveSearchKeywordEntity.toDomain(): ThirtyFiveSearchKeyword {
    return ThirtyFiveSearchKeyword(keyword)
}