package com.example.portfolio.ui.common

import android.Manifest
import android.content.pm.PackageManager

enum class HardwareName(packageManager: String) {
    GPS(PackageManager.FEATURE_LOCATION_GPS)
}

enum class PermissionName(permissionName: ArrayList<String>) {
    GPS(arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
}