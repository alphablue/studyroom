package com.example.fastthirtyfivefinal.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastthirtyfive_domain.usecase.ThirtyFiveMainUseCase
import com.example.fastthirtyfivefinal.di.Dispatcher
import com.example.fastthirtyfivefinal.util.d
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModelOld @Inject constructor(
    mainUseCase: ThirtyFiveMainUseCase
): ViewModel() {
    val productList = mainUseCase.getProductList()

    fun openSearchForm() {
        "open Search From run".d()
    }
}