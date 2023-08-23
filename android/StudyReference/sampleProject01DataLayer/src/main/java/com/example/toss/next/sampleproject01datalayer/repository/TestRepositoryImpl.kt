package com.example.toss.next.sampleproject01datalayer.repository

import com.example.seojin.sampleproject01domainlayer.model.TestModel
import com.example.seojin.sampleproject01domainlayer.repository.TestRepository
import com.example.toss.next.sampleproject01datalayer.datasource.TestDataSource
import com.example.toss.next.sampleproject01datalayer.model.toDomainModel

class TestRepositoryImpl(
    val dataSource: TestDataSource
) : TestRepository{
    override fun getTEstData(): TestModel {
        return dataSource.getTestModelResponse().toDomainModel()
    }
}