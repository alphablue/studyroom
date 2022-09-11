package com.example.portfolio.ui.screen.map

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.FilterTiltShift
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.portfolio.ui.common.HardwareName
import com.example.portfolio.ui.common.PermissionName
import com.example.portfolio.ui.screen.util.observeAsState
import com.example.portfolio.ui.screen.util.permission.PermissionCheck
import com.example.portfolio.viewmodel.MainActivityViewModel
import com.example.portfolio.viewmodel.TestViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun GoogleMapView(
    testViewModel: TestViewModel = viewModel(),
    activityViewModel: MainActivityViewModel
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
            myLocationButtonEnabled = true,
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
        grantedCheck = { permissionGranted = it })

    if (permissionGranted) {

        when (LocalLifecycleOwner.current.lifecycle.observeAsState()) {
            Lifecycle.Event.ON_START -> {
                activityViewModel.startLocationUpdate()
                Log.d("mapView", "ON_START start")
            }

            Lifecycle.Event.ON_RESUME -> {
                activityViewModel.startLocationUpdate()
                Log.d("mapView", "onResume start")
            }
            Lifecycle.Event.ON_PAUSE -> {
                activityViewModel.stopLocationUpdate()
                Log.d("mapView", "onPause start")
            }
            else -> {}
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
                Marker(
                    state = MarkerState(
                        position = seoul
                    ),
                    title = "Singapore",
                    snippet = "Marker in Singapore"
                )

                myLocation?.let {
                    Marker(
                        state = MarkerState(
                            position = LatLng(it.latitude, it.longitude)
                        ),
                        title = "my location",
                        snippet = "lat : ${it.latitude}, log : ${it.longitude}"
                    )
                }

                if (clickLocation.isNotEmpty()) {
                    clickLocation.forEach {
                        Marker(
                            state = MarkerState(
                                position = it
                            ),
                            title = "clicked position",
                            snippet = "lat : ${it.latitude}, log : ${it.longitude}"
                        )
                    }
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
                    .align(Alignment.BottomEnd),
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
                }) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "LocationButton"
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "권한 설정이 필요합니다.", modifier = Modifier.align(Alignment.Center))
        }
    }
}