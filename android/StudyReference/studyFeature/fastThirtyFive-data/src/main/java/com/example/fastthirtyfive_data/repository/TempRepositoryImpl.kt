package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.datasource.TempDataSource
import com.example.fastthirtyfive_domain.model.TempModel
import com.example.fastthirtyfive_domain.repository.TempRepository
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(
    private val dataSource: TempDataSource
): TempRepository {
    override fun getTempModel(): TempModel {
        return dataSource.getTempModel()
    }
}