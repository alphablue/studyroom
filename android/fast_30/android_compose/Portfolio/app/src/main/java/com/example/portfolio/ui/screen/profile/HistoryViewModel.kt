package com.example.portfolio.ui.screen.profile

import androidx.compose.runtime.mutableStateListOf
import com.example.portfolio.di.repository.RoomRepository
import com.example.portfolio.localdb.OrderHistory
import com.example.portfolio.viewmodel.BaseViewModel
import com.example.portfolio.viewmodel.DispatcherProvider
import com.example.portfolio.viewmodel.onIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    dispatcherProvider: DispatcherProvider
): BaseViewModel(dispatcherProvider){

    val allHistoryData = mutableStateListOf<OrderHistory>()

    fun getAllHistory(userId: String) = onIO{
        allHistoryData.clear()
        allHistoryData.addAll(roomRepository.getAllOrderHistory(userId))
    }
}