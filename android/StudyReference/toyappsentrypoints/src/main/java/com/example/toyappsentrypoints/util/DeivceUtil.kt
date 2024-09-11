package com.example.toyappsentrypoints.util

import android.os.Build
import java.util.Locale

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL

    fun capitalize(s: String): String {
        if (s.isEmpty()) return ""

        val first = s.first()
        return if (first.isUpperCase()) return s
        else first.uppercase() + s.drop(1)
    }

    return if (model
        .lowercase(Locale.getDefault())
        .startsWith(manufacturer.lowercase(Locale.getDefault()))
    ) {
        capitalize(model)
    } else {
        "${capitalize(manufacturer)} $model"
    }
}