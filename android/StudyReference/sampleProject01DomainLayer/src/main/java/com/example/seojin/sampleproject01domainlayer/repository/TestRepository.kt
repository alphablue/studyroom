package com.example.seojin.sampleproject01domainlayer.repository

import com.example.seojin.sampleproject01domainlayer.model.TestModel

interface TestRepository {
    fun getTestData(): TestModel
}