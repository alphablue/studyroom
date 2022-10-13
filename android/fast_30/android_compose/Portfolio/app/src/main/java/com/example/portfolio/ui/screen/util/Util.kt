package com.example.portfolio.ui.screen.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.IllegalStateException
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun Context.findActivity(): Activity {
    var context = this

    while (context is ContextWrapper) {
        if (context is Activity) return context

        context = context.baseContext
    }

    throw IllegalStateException("no activity")
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine {
    continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}

suspend fun ImageCapture.takePicture(executor: Executor): File {
    val photoFile = withContext(Dispatchers.IO){
        kotlin.runCatching {
            File.createTempFile("image", "jpg")
        }.getOrElse { ex ->
            Log.d("CameraPreview", "Failed to create temporary file", ex)
            File("/dev/null")
        }
    }

    return suspendCoroutine { continuation ->
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback{
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                continuation.resume(photoFile)
            }

            override fun onError(ex: ImageCaptureException) {
                Log.d("CameraPreview", "Image capture failed", ex)
                continuation.resumeWithException(ex)
            }
        })
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

fun Float.number2Digits(): String {
    return String.format("%.2f", this)
}

fun Float.number1Digits(): String {
    return String.format("%.1f", this)
}

fun localRoomLikeKey(userId: String, resId: String) = "${userId}_$resId"