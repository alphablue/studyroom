package com.example.toss.next.sampleproject01datalayer.repository

import com.example.seojin.sampleproject01domainlayer.model.TempModel
import com.example.seojin.sampleproject01domainlayer.repository.TempRepository
import com.example.toss.next.sampleproject01datalayer.datasource.TempDataSource
import javax.inject.Inject

class TempRepositoryImpl @Inject constructor(private val dataSource : TempDataSource) : TempRepository {
    override fun getTempModel(): TempModel {
        return dataSource.getTempModel()
    }

}