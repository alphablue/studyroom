package com.example.studystartingpoint.systemArch.dateModule

import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.Locale

fun daysOfWeek(firstDayOfWeek: DayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek): List<DayOfWeek> {
    val pivot = 7 - firstDayOfWeek.ordinal
    val daysOfWeek = DayOfWeek.entries
    return daysOfWeek.takeLast(pivot) + daysOfWeek.dropLast(pivot)
}