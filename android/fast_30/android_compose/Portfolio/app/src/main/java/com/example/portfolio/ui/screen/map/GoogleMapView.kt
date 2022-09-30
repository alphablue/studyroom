package com.example.portfolio.ui.screen.map

import android.location.Location
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FilterTiltShift
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.theme.lightSecondaryBlue
import com.example.portfolio.ui.theme.textColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapView(
    activityViewModel: MainActivityViewModel,
    upPress: () -> Unit,
) {
    val seoul = LatLng(37.5666805, 126.9784147)

    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(seoul, 10f)
    }

    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false
        )
    }
    val properties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                isBuildingEnabled = true,
            )
        )
    }

    var permissionGranted by remember { mutableStateOf(false) }

    PermissionCheck(
        permissionName = PermissionName.GPS,
        hardwareName = HardwareName.GPS,
        grantedCheck = { permissionGranted = it }
    )

    if (permissionGranted) {

        LaunchedEffect(key1 = true) {
            activityViewModel.getLocation { lastLocation ->
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(lastLocation.latitude, lastLocation.longitude),
                    20f
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            )

            MapViewTopBar(upPress = upPress, Modifier.align(Alignment.TopCenter))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { }) {
                    Image(
                        imageVector = Icons.Outlined.FilterTiltShift,
                        contentDescription = "mapCenterMarker"
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-24).dp, y = (-60).dp),
                onClick = {

                    activityViewModel.realTimeUserLocation?.let {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            20f
                        )
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "LocationButton",
                    tint = lightSecondaryBlue
                )
            }

            TextButton(
                onClick = {
                    activityViewModel.userSettingLocation = Location("").apply {
                        latitude = cameraPositionState.position.target.latitude
                        longitude = cameraPositionState.position.target.longitude
                    }
                    upPress()
                },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(contentColor = lightSecondaryBlue)
            ) {
                Text(
                    text = "이 위치로 설정하기",
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "위치 권한 확인 중 입니다.", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun MapViewTopBar(
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(modifier = modifier.statusBarsPadding().fillMaxWidth()) {
        IconButton(onClick = upPress, modifier = Modifier.align(Alignment.Top)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = textColor,
                contentDescription = "back"
            )
        }

        Text(
            "위치를 선택해 주세요.",
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}