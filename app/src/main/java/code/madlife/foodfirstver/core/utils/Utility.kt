package code.madlife.foodfirstver.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings

object Utility {
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context?): String {
        return Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDeviceInfo(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return "$manufacturer $model"
    }

    fun getAndroidVersion(): String {
        val sdkInt = Build.VERSION.SDK_INT
        return Build.VERSION.RELEASE
    }

}