package com.example.portfolio.ui.screen.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch

@Composable
fun GoogleMapView() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    val textLocation = LatLng(35.826, 128.736)

    val context = LocalContext.current
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    var myLocation by remember {
        mutableStateOf<Location?>(null)
    }

    val clickLocation = remember {
        mutableStateListOf<LatLng>()
    }

    val uiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = true,
            zoomControlsEnabled = false)
    }
    val properties by remember { mutableStateOf(
        MapProperties(
            isMyLocationEnabled = true,
            isBuildingEnabled = true,
        ))
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen","PERMISSION DENIED")
        }
    }

    LaunchedEffect(Unit ) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        onMyLocationButtonClick = {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ) {
//                buildAlertMessageNoGps(context)
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
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(
                position = singapore
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

        if(clickLocation.isNotEmpty()) {
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

        Marker(
            state = MarkerState(position = textLocation),
            title = "test location",
            flat = false,
            infoWindowAnchor = Offset(0.2f, 0.3f)
        )

    }

}