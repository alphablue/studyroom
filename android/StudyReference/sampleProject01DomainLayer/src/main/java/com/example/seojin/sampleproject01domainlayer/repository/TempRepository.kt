package com.example.seojin.sampleproject01domainlayer.repository

import com.example.seojin.sampleproject01domainlayer.model.TempModel

interface TempRepository {
    fun getTempModel() : TempModel
}