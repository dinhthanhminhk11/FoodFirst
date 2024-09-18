package code.madlife.foodfirstver.presentation.activity.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {
    val addressData: MutableLiveData<String> = MutableLiveData()
    val ctyData: MutableLiveData<String> = MutableLiveData()
    private fun getAddress(context: Context, lati: Double, longi: Double) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            geocoder.getFromLocation(lati, longi, 1).let {
                val obj = it!![0]
                var add = obj.getAddressLine(0)
                Log.e("MinhLocation", add)
                add = "$add\n${obj.countryName}"
                add = "$add\n${obj.countryCode}"
                add = "$add\n${obj.adminArea}"
                add = "$add\n${obj.postalCode}"
                add = "$add\n${obj.subAdminArea}"
                add = "$add\n${obj.locality}"
                add = "$add\n${obj.subThoroughfare}"
                addressData.postValue(add)
                ctyData.postValue(obj.adminArea)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {
        val locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                LocationServices.getFusedLocationProviderClient(context.applicationContext)
                    .removeLocationUpdates(this)
                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                    val latestlocIndex = locationResult.locations.size - 1
                    val lati = locationResult.locations[latestlocIndex].latitude
                    val longi = locationResult.locations[latestlocIndex].longitude
                    val locationYouSelf = Location("locationYourSelf")
                    locationYouSelf.longitude = longi
                    locationYouSelf.latitude = lati

                    getAddress(context, locationYouSelf.latitude, locationYouSelf.longitude)
//                    locationYouSelfMutableLiveData.postValue(locationYouSelf)
                }
            }
        }
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }
}