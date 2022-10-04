package com.example.portfolio.ui.screen.util

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

fun lifeCycleDetector(
    lifecycle: Lifecycle.Event,
    onCreate: () -> Unit = { Log.d("lifeCycleDetector", "onCreate")},
    onStart: () -> Unit = {Log.d("lifeCycleDetector", "onStart")},
    onResume: () -> Unit= {Log.d("lifeCycleDetector", "onResume")},
    onPause: () -> Unit= {Log.d("lifeCycleDetector", "onPause")},
    onStop: () -> Unit= {Log.d("lifeCycleDetector", "onStop")},
    onDestroy: () -> Unit= {Log.d("lifeCycleDetector", "onDestroy")},
    onAny: () -> Unit= {Log.d("lifeCycleDetector", "onAny")},
) {
    when(lifecycle) {
        Lifecycle.Event.ON_CREATE -> onCreate()
        Lifecycle.Event.ON_START -> onStart()
        Lifecycle.Event.ON_RESUME -> onResume()
        Lifecycle.Event.ON_PAUSE -> onPause()
        Lifecycle.Event.ON_STOP -> onStop()
        Lifecycle.Event.ON_DESTROY -> onDestroy()
        Lifecycle.Event.ON_ANY -> onAny()
    }
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