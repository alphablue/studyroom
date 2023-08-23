package com.example.toss.next.sampleproject01datalayer.datasource

import com.example.toss.next.sampleproject01datalayer.model.TestModelResponse

class TestDataSource {
    fun getTestModelResponse(): TestModelResponse {
        return TestModelResponse("response")
    }
}