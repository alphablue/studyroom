package com.example.fastthirtyfivefinal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fastthirtyfive_domain.model.TempModel
import com.example.fastthirtyfive_domain.usecase.TempUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val useCase: TempUseCase
): ViewModel() {

    fun getTempModel(): TempModel {
        return useCase.getTempModel()
    }
}