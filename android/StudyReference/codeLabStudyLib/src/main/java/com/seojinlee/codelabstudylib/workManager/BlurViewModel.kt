/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seojinlee.codelabstudylib.workManager

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.seojinlee.codelabstudylib.R


class BlurViewModel(application: Application) : ViewModel() {

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null

    private val workerManager = WorkManager.getInstance(application)

    init {
        imageUri = getImageUri(application.applicationContext)
    }
    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {

        // step 6 워크매니저 체인을 위한 작업  --- 1
        var continuation = workerManager
            .beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))


//        -------------- 수동 으로 체인을 거는 법
//        workerManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))  // step 4
//        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()               // step 5에서 변경
//            .setInputData(createInputDataForUri())
//            .build()
//
//        // 1번 작업 후 2번째 작업의 순서 추가  ---- 2
//        continuation = continuation.then(blurRequest)
//
//
//        val save = OneTimeWorkRequest.Builder(SaveImageToFileWorker::class.java).build()
//        // 2번 작업 후의 작업 추가 --- 3
//        continuation = continuation.then(save)

//        --------------- end

//        ------------- workmanager 에서 체인의 유연함을 보여줌
        for(i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            if(i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .build()

        continuation = continuation.then(save)

//        workerManager.enqueue(blurRequest)
        continuation.enqueue() // 정의된 작업 순서 대로 수행
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
            .build()
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    class BlurViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
                BlurViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
