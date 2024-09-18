package code.madlife.foodfirstver.core.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import code.madlife.foodfirstver.core.common.Constants

@SuppressLint("UseRequireInsteadOfGet")
fun checkLocationPermission(activity: FragmentActivity): Boolean {
    val permissionResult = ContextCompat.checkSelfPermission(
        activity, Manifest.permission.ACCESS_FINE_LOCATION
    )
    return permissionResult == PackageManager.PERMISSION_GRANTED
}

fun requestLocationPermission(activity: FragmentActivity) {
    activity.requestPermissions(
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        Constants.LOCATION_PERMISSION_REQUEST_CODE
    )
}