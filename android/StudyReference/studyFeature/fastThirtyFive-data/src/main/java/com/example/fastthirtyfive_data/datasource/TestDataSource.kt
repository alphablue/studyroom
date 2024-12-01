package com.example.fastthirtyfive_data.datasource

import com.example.fastthirtyfive_data.model.TestModelResponse

class TestDataSource {
    fun getTestModelResponse(): TestModelResponse {
        return TestModelResponse("RESPONSE")
    }
}