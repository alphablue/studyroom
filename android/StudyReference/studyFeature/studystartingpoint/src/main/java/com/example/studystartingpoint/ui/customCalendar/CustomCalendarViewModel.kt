package com.example.studystartingpoint.ui.customCalendar

import androidx.compose.animation.core.Animatable
import androidx.lifecycle.ViewModel

class CustomCalendarViewModel : ViewModel() {
    private val _calendarHeightState = Animatable(0f)
    val calendarHeightState = _calendarHeightState.asState()

//    fun setDragOffSet(drag: Float, maxDrag: Int) {
//        _calendarHeightState.snapTo((_calendarHeightState.value - drag))
//    }
}