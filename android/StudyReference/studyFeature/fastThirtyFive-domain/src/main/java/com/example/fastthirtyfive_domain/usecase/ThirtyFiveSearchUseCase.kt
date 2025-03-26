package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.ThirtyFiveProduct
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchFilter
import com.example.fastthirtyfive_domain.model.ThirtyFiveSearchKeyword
import com.example.fastthirtyfive_domain.repository.ThirtyFiveSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ThirtyFiveSearchUseCase @Inject constructor(
    private val searchRepository: ThirtyFiveSearchRepository
) {
    suspend fun search(searchKeyword: ThirtyFiveSearchKeyword, filters: List<ThirtyFiveSearchFilter>): Flow<List<ThirtyFiveProduct>> {
        return searchRepository.search(searchKeyword, filters)
    }

    fun getSearchKeyword(): Flow<List<ThirtyFiveSearchKeyword>> {
        return searchRepository.getSearchKeywords()
    }
}