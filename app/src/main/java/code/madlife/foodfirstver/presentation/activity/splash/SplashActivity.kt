package code.madlife.foodfirstver.presentation.activity.splash

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import code.madlife.foodfirstver.R
import code.madlife.foodfirstver.core.common.Constants.LOCATION_PERMISSION_REQUEST_CODE
import code.madlife.foodfirstver.core.utils.checkLocationPermission
import code.madlife.foodfirstver.core.utils.requestLocationPermission
import dagger.hilt.android.AndroidEntryPoint


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private val textTest: TextView by lazy { findViewById<TextView>(R.id.textTest) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        if (checkLocationPermission(this)) {
            viewModel.getCurrentLocation(this)
        } else {
            requestLocationPermission(this)
        }

        viewModel.ctyData.observe(this){
            textTest.text = it.toString()
        }

    }


    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionsResult(requestCode, permissions, grantResults)
    }


    private fun handlePermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getCurrentLocation(this)
            } else {

                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}