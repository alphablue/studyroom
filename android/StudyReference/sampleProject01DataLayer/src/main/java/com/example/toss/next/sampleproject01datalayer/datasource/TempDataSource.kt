package com.example.toss.next.sampleproject01datalayer.datasource

import com.example.seojin.sampleproject01domainlayer.model.TempModel
import javax.inject.Inject

class TempDataSource @Inject constructor() {
    fun getTempModel() : TempModel {
        return TempModel("testModel")
    }
}