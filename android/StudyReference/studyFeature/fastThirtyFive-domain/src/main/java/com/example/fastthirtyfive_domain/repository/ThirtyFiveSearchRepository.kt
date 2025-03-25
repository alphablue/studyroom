package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import kotlinx.coroutines.flow.Flow

interface ThirtyFiveSearchRepository {
    suspend fun search(searchKeyword: ThirtyFiveSearchKeyword): Flow<List<ThirtyFiveProduct>>

    fun getSearchKeywords(): Flow<List<ThirtyFiveSearchKeyword>>
}