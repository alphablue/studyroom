package com.example.fastthirtyfive_data.util

import android.util.Log
import com.example.fastthirtyfive_data.util.LogType.Debug
import com.example.fastthirtyfive_data.util.LogType.Error
import com.example.fastthirtyfive_data.util.LogType.Info
import com.example.fastthirtyfive_data.util.LogType.Notice
import com.example.fastthirtyfive_data.util.LogType.Verbose
import com.example.fastthirtyfive_data.util.LogType.Warning
import java.util.Locale

fun String?.v(tag: String = ExtLoggerImpl.TAG) {
    displayLog(this ?: "empty Message", tag, Verbose)
}

fun String?.d(tag: String = ExtLoggerImpl.TAG) {
    displayLog(this ?: "empty Message", tag, Debug)
}

fun String?.i(tag: String = ExtLoggerImpl.TAG) {
    displayLog(this ?: "empty Message", tag, Info)
}

fun String?.w(tag: String = ExtLoggerImpl.TAG) {
    displayLog(this ?: "empty Message", tag, Warning)
}

fun String?.e(tag: String = ExtLoggerImpl.TAG) {
    displayLog(this ?: "empty Message", tag, Error)
}

private object ExtLoggerImpl {
    private fun convertMessage(message: String): String {
        val element = Thread.currentThread().stackTrace[4]
        val fileName = element.fileName
        return String.format(
            Locale.getDefault(),
            "%s::%s() #%d] %s",
            fileName.substring(0, fileName.indexOf(".")),
            element.methodName,
            element.lineNumber,
            message
        )
    }

    const val TAG = "ThirtyFive"

    fun d(tag: String = TAG, message: String) {
        Log.d(tag, convertMessage(message))
    }

    fun i(tag: String = TAG, message: String) {
        Log.i(tag, convertMessage(message))
    }

    fun v(tag: String = TAG, message: String) {
        Log.v(tag, convertMessage(message))
    }

    fun w(tag: String = TAG, message: String) {
        Log.w(tag, convertMessage(message))
    }

    fun e(tag: String = TAG, message: String) {
        Log.e(tag, convertMessage(message))
    }
}

private enum class LogType {
    Verbose, Debug, Info, Notice, Warning, Error
}

private fun displayLog(
    message: String,
    tag: String,
    logType: LogType
) {
    var fileName =  ""
    var lineNumber = ""
    runCatching {
        Thread.currentThread().stackTrace
            .firstOrNull()
            ?.let {
                fileName = it.fileName
                lineNumber = it.lineNumber.toString()
            }
    }.onSuccess {
        when(logType) {
            Verbose -> {
                ExtLoggerImpl.v(tag, "${String.format("%s:%s", fileName, lineNumber)} -> $message")
            }
            Debug -> {
                ExtLoggerImpl.d(tag, "${String.format("%s:%s", fileName, lineNumber)} -> $message")
            }
            Info -> {
                ExtLoggerImpl.i(tag, "${String.format("%s:%s", fileName, lineNumber)} -> $message")
            }
            Notice, Warning -> {
                ExtLoggerImpl.w(tag, "${String.format("%s:%s", fileName, lineNumber)} -> $message")
            }
            Error -> {
                ExtLoggerImpl.e(tag, "${String.format("%s:%s", fileName, lineNumber)} -> $message")
            }
        }
    }.onFailure {
        Log.e(ExtLoggerImpl.TAG, it.stackTraceToString())
    }
}