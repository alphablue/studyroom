package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.TestModel

interface TestRepository {
    fun getTestData(): TestModel
}