package com.example.portfolio.ui.screen.map

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FilterTiltShift
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.portfolio.MainActivityViewModel
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.ui.theme.lightSecondaryBlue
import com.example.portfolio.ui.theme.textColor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GoogleMapView(
    activityViewModel: MainActivityViewModel,
    upPress: () -> Unit,
) {
    val seoul = LatLng(37.5666805, 126.9784147)
    var myLocation by remember {
        mutableStateOf<Location?>(null)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = if (myLocation != null) {
            CameraPosition.fromLatLngZoom(
                LatLng(myLocation!!.latitude, myLocation!!.longitude),
                10f
            )
        } else {
            CameraPosition.fromLatLngZoom(seoul, 10f)
        }
    }

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val clickLocation = remember {
        mutableStateListOf<LatLng>()
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
                myLocation = lastLocation

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
                onMyLocationButtonClick = {
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    ) {
                        Toast.makeText(context, "don't have gps", Toast.LENGTH_SHORT).show()
                        return@GoogleMap true
                    }
                    Toast.makeText(context, "click on my location", Toast.LENGTH_SHORT).show()
                    return@GoogleMap false

                },
                onMyLocationClick = {
                    myLocation = it
                },
                onMapClick = {
                    clickLocation.add(it)
                },
                properties = properties,
                uiSettings = uiSettings,
                cameraPositionState = cameraPositionState
            ) {

                myLocation?.let {
                    Marker(
                        state = MarkerState(
                            position = LatLng(it.latitude, it.longitude)
                        ),
                        title = "my location",
                        snippet = "lat : ${it.latitude}, log : ${it.longitude}"
                    )
                }
            }

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
                Text(
                    text = "Camera Moving : ${cameraPositionState.isMoving}" +
                            "\n Lat and lng : ${cameraPositionState.position.target.latitude}, ${cameraPositionState.position.target.longitude}",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            IconButton(
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = (-24).dp, y = (-60).dp),
                onClick = {
//                    activityViewModel.getLocation()
                    activityViewModel.realTimeUserLocation?.let {
                        myLocation = it
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            20f
                        )
//                        testViewModel.getData("json", it.latitude, it.longitude)
                    }
                    Toast.makeText(
                        context,
                        "click iconButton myLocation: $myLocation",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "LocationButton"
                )
            }

            TextButton(
                onClick = { upPress() },
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
            Text(text = "권한 설정이 필요합니다.", modifier = Modifier.align(Alignment.Center))
        }
    }
}