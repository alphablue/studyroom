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

@Composable
fun Lifecycle.observeAsState(): Lifecycle.Event {
    var state by remember {
        mutableStateOf(Lifecycle.Event.ON_ANY)
    }

    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }

    return state
}