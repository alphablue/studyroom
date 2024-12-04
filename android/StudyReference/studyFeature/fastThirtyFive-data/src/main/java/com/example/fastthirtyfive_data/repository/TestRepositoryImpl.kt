package com.example.fastthirtyfive_data.repository

import com.example.fastthirtyfive_data.datasource.TestDataSource
import com.example.fastthirtyfive_data.model.toDomainModel
import com.example.fastthirtyfive_domain.model.TestModel
import com.example.fastthirtyfive_domain.repository.TestRepository

/**
* 이렇게 하는 이유? response 의 필드가 채워질 것을 확신 할 수 없기 때문 (크래시 발생)
* */
class TestRepositoryImpl(
    private val dataSource: TestDataSource
): TestRepository {
    override fun getTestData(): TestModel? {
        return dataSource.getTestModelResponse().toDomainModel()
    }
}