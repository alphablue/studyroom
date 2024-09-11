package com.example.toyappsentrypoints.util

import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

fun Any.d(tag: String = "") {
    Logger.addLogAdapter(AndroidLogAdapter(
        PrettyFormatStrategy.newBuilder()
            .methodCount(3)
            .tag(tag.ifEmpty { "PRETTY_LOGGER" })
            .build()
    ))

    Logger.d(this)
}