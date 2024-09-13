package code.madlife.foodfirstver.presentation

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import code.madlife.foodfirstver.encryption.Login

import code.madlife.foodfirstver.presentation.core.eventbus.ApplicationEvent
import code.madlife.foodfirstver.presentation.core.utils.Utility
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val key = "Sxqa3KHdPs1uSAjNOSVmcvE/OhUMH5eZXQLtoRSUd6G0a22PPPwS38/F/lryy3Cz"
        val iv = "foRC3P7jiVX9Z4Fgj0nm9QGP1H7eEEj9DW3z7VloN920a22PPPwS38/F/lryy3Cz"
        Login.init(key, iv)

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