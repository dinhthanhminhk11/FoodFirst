package code.madlife.foodfirstver.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import code.madlife.foodfirstver.BuildConfig
import code.madlife.foodfirstver.encryption.Login
import code.madlife.foodfirstver.encryption.LoginDev

import code.madlife.foodfirstver.presentation.core.eventbus.ApplicationEvent
import code.madlife.foodfirstver.presentation.core.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Login.init(BuildConfig.KEY_128, BuildConfig.IV_128)
        ProcessLifecycleOwner.get().lifecycle.addObserver(defaultLifecycleObserver)
        Utility.setAppMode(Utility.isAppDarkMode())
    }

    var defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            EventBus.getDefault().post(ApplicationEvent(ApplicationEvent.Type.OPEN_APP))
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            EventBus.getDefault().post(ApplicationEvent(ApplicationEvent.Type.HIDDEN_APP))
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            Timber.e("destroyed app")
        }
    }
}