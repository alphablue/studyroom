package com.example.seojin.sampleproject01domainlayer.usecase

import com.example.seojin.sampleproject01domainlayer.model.TempModel
import com.example.seojin.sampleproject01domainlayer.repository.TempRepository
import javax.inject.Inject

class TempUseCase @Inject constructor(private val repository : TempRepository) {
    fun getTempModel(): TempModel {
        return repository.getTempModel()
    }
}