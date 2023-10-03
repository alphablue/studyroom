package com.example.seojin.sampleProject01PresentationLayer.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TempViewModel @Inject constructor() : ViewModel() {

    // viewModel에서 사용할 UseCase는 DomainLayer 에서 작업 되어야 함
    fun getTempViewModel() {

    }
}