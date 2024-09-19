package code.madlife.foodfirstver

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.MySharedPreferences
import code.madlife.foodfirstver.core.common.showToastError
import code.madlife.foodfirstver.core.common.showToastSuccess
import code.madlife.foodfirstver.core.utils.checkLocationPermission
import code.madlife.foodfirstver.core.utils.requestLocationPermission
import code.madlife.foodfirstver.data.model.user.User
import code.madlife.foodfirstver.data.model.user.UserClient
import code.madlife.foodfirstver.databinding.ActivityMainBinding
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.presentation.NavigationManager
import code.madlife.foodfirstver.presentation.core.base.BaseActivity
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), FragmentManager.OnBackStackChangedListener {
    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.addOnBackStackChangedListener(this)
        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)


        if (checkLocationPermission(this)) {
            viewModel.getCurrentLocation(this)
        } else {
            requestLocationPermission(this)
        }


        val token = MySharedPreferences.getInstance(this)
            .getString(Constants.TOKEN_USER, "")

        if (!token.isNullOrEmpty()) {
            Log.e("MinhToken", token)
            viewModel.loginByToken(token)
        }

        viewModel.authState.observe(this) {
            when (it) {
                is AuthState.Success -> {
                    val gson = Gson()
                    val user: User =
                        gson.fromJson(Login.decryptData(it.data.data.toString()), User::class.java)
                    UserClient.saveUser(MySharedPreferences.getInstance(this), user)
                }

                is AuthState.Fail -> {
                    Log.e("MinhToken", "Đéo có token")
                }

                AuthState.Loading -> {

                }
            }
        }

    }

    override fun onBackStackChanged() {

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
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.getCurrentLocation(this)
            } else {
                showToastError(
                    activity = this,
                    content = getString(R.string.location_permission_denied)
                )
            }
        }
    }
}