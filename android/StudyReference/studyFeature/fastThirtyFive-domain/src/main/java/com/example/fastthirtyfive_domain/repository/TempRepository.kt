package com.example.fastthirtyfive_domain.repository

import com.example.fastthirtyfive_domain.model.TempModel

interface TempRepository {
    fun getTempModel() : TempModel
}