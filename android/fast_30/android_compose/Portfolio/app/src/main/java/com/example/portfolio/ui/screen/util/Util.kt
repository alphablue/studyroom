package com.example.portfolio.ui.screen.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import java.lang.IllegalStateException

fun Context.findActivity(): Activity {
    var context = this

    while (context is ContextWrapper) {
        if (context is Activity) return context

        context = context.baseContext
    }

    throw IllegalStateException("no activity")
}
