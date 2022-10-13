package com.example.portfolio.ui.screen.home.detailview

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.portfolio.di.modules.firebasemodule.FirebaseObject
import com.example.portfolio.model.uidatamodels.DisPlayReview
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.common.StarRatingBar
import com.example.portfolio.ui.screen.util.executor
import com.example.portfolio.ui.screen.util.getCameraProvider
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.screen.util.permission.PermissionDialog
import com.example.portfolio.ui.screen.util.takePicture
import kotlinx.coroutines.launch
import java.io.File

const val DETAIL_REVIEW_VIEW = "리뷰"

@Composable
fun DetailReviewView(
    upPress: () -> Unit
) {
    val getReviewData = remember { mutableStateListOf<DisPlayReview>() }

    LaunchedEffect(true) {
        FirebaseObject.getTestReview {
            getReviewData.addAll(it)
        }
    }

//    Column {
//        for ((reviewInfo, userInfo) in getReviewData) {
//            DrawReview(
//                userNickName = userInfo.name,
//                userProfileImg = userInfo.profileImage,
//                ratingValue = reviewInfo.rating.toFloat(),
//                reviewDate = reviewInfo.date,
//                contentText = reviewInfo.content
//            )
//        }
//    }
    CameraView(
        upPress = upPress
    )
}

@Composable
fun DrawReview(
    userNickName: String,
    userProfileImg: String,
    ratingValue: Float,
    reviewDate: String,
    takePictureData: String? = null,
    contentText: String
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier.wrapContentHeight()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(userProfileImg)
                    .build(),
                contentDescription = "userProfileImage",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(100)),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = userNickName)
                StarRatingBar(rateCount = ratingValue)
            }
            Text(text = reviewDate)
        }
        if (takePictureData != null) {
            AsyncImage(model = takePictureData, contentDescription = "contextImage")
        }
        Text(text = contentText)
    }
}

@Composable
fun CameraView(
    upPress: () -> Unit
) {
    val context = LocalContext.current

    var cameraPermissionCheck by remember{ mutableStateOf(false)}

    PermissionCheck(
        permissionName = PermissionName.CAMERA,
        hardwareName = HardwareName.CAMERA,
        grantedCheck = { state -> cameraPermissionCheck = state}
    )

    if(cameraPermissionCheck) {
        MainContent()
    } else {
        PermissionDialog(
            onDismissClickEvent = { },
            confirmButtonEvent = {
                val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                settingIntent.data = uri
                startActivity(context, settingIntent, null)
            },
            dismissButtonEvent = upPress
        )
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
                .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build())}

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
                    imageCaptureUseCase.takePicture(context.executor).let{
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
            } catch(ex: Exception) {
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

            onUseCase(Preview.Builder()
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
    modifier: Modifier = Modifier
) {
    val emptyImageUri = Uri.parse("file://dev/null")
    var imageUri by remember { mutableStateOf(emptyImageUri)}

    if (imageUri != emptyImageUri) {
        Box(modifier = modifier) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUri,
                contentDescription = "capture image"
            )

            Button(
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = {
                    imageUri = emptyImageUri
                }
            ) {
                Text(text = "Remove image")
            }
        }
    } else {
        CameraCapture(
            modifier = modifier,
            onImageFile = { file ->
                imageUri = file.toUri()
            }
        )
    }
}