package com.example.portfolio.ui.screen.home.detailview.review

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.portfolio.ui.common.EMPTY_IMAGE_URI
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.*
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import kotlinx.coroutines.launch
import java.io.File

const val cameraRoute = "cameraView"
const val captureUriKey = "captureUri"

@Composable
fun CameraView(
    upPress: () -> Unit,
    addUriOfBackStack: (String, Uri) -> Unit
) {
    val context = LocalContext.current
    var cameraPermissionCheck by remember { mutableStateOf(false) }
    var checkLifeCycle by remember { mutableStateOf(false)}
    val lifecycle = LocalLifecycleOwner.current.lifecycle.observeAsState()

    lifeCycleDetector(
        lifecycle,
        onResume = {
            checkLifeCycle = true
        },
        onPause = {
            checkLifeCycle = false
        }
    )

    if(checkLifeCycle) {
        PermissionCheck(
            permissionName = PermissionName.CAMERA,
            hardwareName = HardwareName.CAMERA,
            grantedCheck = { cameraPermissionCheck = it },
            onDismissClickEvent = {},
            dismissButtonEvent = {
                upPress()
            },
            confirmButtonEvent = {
                goToAppDetailSetting(context = context)
            }
        )
    }

    Log.d("cameraview", "permisson check :: $cameraPermissionCheck")

    if (cameraPermissionCheck) {
        MainContent(upPress = upPress, addUriOfBackStack = addUriOfBackStack)
    }
}

@Composable
fun CameraCapture(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (File) -> Unit = {}
) {
    val imageCaptureUseCase by remember {
        mutableStateOf(
            ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }

        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onUseCase = {
                previewUseCase = it
            }
        )

        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                coroutineScope.launch {
                    imageCaptureUseCase.takePicture(context.executor).let {
                        onImageFile(it)
                    }
                }
            }
        ) {
            Text(text = "사진촬영")
        }

        LaunchedEffect(previewUseCase) {
            val cameraProvider = context.getCameraProvider()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
                )
            } catch (ex: Exception) {
                Log.d("CameraPreview", "Use case binding failed", ex)
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    onUseCase: (UseCase) -> Unit = {}
) {
    AndroidView(
        modifier = modifier,
        factory = { viewContext ->
            val previewView = PreviewView(viewContext).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
            previewView
        }
    )
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    addUriOfBackStack: (String, Uri) -> Unit
) {
    val emptyImageUri = Uri.parse("file://dev/null")
    var imageUri by remember { mutableStateOf(emptyImageUri) }
    var showGallerySelect by remember {
        mutableStateOf(false)
    }

    if (imageUri != emptyImageUri) {
        Box(modifier = modifier) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUri,
                contentDescription = "capture image"
            )


            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
            ) {
                Button(
                    onClick = {
                        imageUri = emptyImageUri
                    }
                ) {
                    Text(text = "다시찍기")
                }

                Button(onClick = {
                    addUriOfBackStack("captureUri", imageUri)
                }) {
                    Text("선택완료")
                }
            }

        }
    } else {

        if (showGallerySelect) {
            GallerySelect(
                onImageUri = { uri ->
                    showGallerySelect = false
                    imageUri = uri
                },
                upPress = upPress
            )
        } else {
            Box(modifier = modifier) {
                CameraCapture(
                    modifier = modifier,
                    onImageFile = { file ->
                        imageUri = file.toUri()
                    }
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(4.dp),
                    onClick = {
                        showGallerySelect = true
                    }
                ) {
                    Text("사진선택")
                }
            }
        }
    }
}

@Composable
fun GallerySelect(
    onImageUri: (Uri) -> Unit = {},
    upPress: () -> Unit
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_IMAGE_URI)
        }
    )
    var permissionCheck by remember { mutableStateOf(false) }

    var checkLifeCycle by remember { mutableStateOf(false)}
    val lifecycle = LocalLifecycleOwner.current.lifecycle.observeAsState()

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    lifeCycleDetector(
        lifecycle,
        onResume = {
            checkLifeCycle = true
        },
        onPause = {
            checkLifeCycle = false
        }
    )

    if(checkLifeCycle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            PermissionCheck(
                permissionName = PermissionName.MEDIA,
                hardwareName = HardwareName.MEDIA,
                grantedCheck = { state ->
                    permissionCheck = state
                },
                confirmButtonEvent = {
                    upPress()
                    goToAppDetailSetting(context)
                },
                dismissButtonEvent = {
                    upPress()
                },
                onDismissClickEvent = {}
            )

            if (permissionCheck) {
                LaunchGallery()
            }
        } else {
            LaunchGallery()
        }
    }
}