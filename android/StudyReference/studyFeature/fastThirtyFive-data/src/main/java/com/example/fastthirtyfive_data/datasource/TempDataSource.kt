package com.example.fastthirtyfive_data.datasource

import com.example.fastthirtyfive_domain.model.TempModel
import javax.inject.Inject

class TempDataSource @Inject constructor(
) {
    fun getTempModel() : TempModel {
        return TempModel("testModel")
    }
}