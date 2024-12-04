package com.example.fastthirtyfive_domain.usecase

import com.example.fastthirtyfive_domain.model.TempModel
import com.example.fastthirtyfive_domain.repository.TempRepository
import javax.inject.Inject

class TempUseCase @Inject constructor(
    private val repository: TempRepository
) {
    fun getTempModel() : TempModel {
        return repository.getTempModel()
    }
}