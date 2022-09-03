package com.example.portfolio.ui.common

import android.Manifest
import android.content.pm.PackageManager

enum class HardwareName(val packageManager: ArrayList<String>) {
    GPS(arrayListOf(PackageManager.FEATURE_LOCATION_GPS, PackageManager.FEATURE_LOCATION_NETWORK))
}

enum class PermissionName(val permissionName: ArrayList<String>) {
    GPS(arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
}