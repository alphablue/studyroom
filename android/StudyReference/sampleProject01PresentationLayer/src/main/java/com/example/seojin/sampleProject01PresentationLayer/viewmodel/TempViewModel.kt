package com.example.seojin.sampleProject01PresentationLayer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.seojin.sampleproject01domainlayer.model.TempModel
import com.example.seojin.sampleproject01domainlayer.usecase.TempUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor(
    private val useCase: TempUseCase
) : ViewModel() {

    // viewModel에서 사용할 UseCase는 DomainLayer 에서 작업 되어야 함
    fun getTempModel() : TempModel {
        return useCase.getTempModel()
    }
}