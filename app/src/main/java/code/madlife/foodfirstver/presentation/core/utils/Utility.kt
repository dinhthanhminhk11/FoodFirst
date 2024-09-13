package code.madlife.foodfirstver.presentation.core.utils

import androidx.appcompat.app.AppCompatDelegate
import code.madlife.foodfirstver.core.utils.PreferenceHelper
import code.madlife.foodfirstver.presentation.core.common.Constants
import code.madlife.foodfirstver.presentation.core.common.NightMode


object Utility {
    fun setAppMode(mode: NightMode) {
        AppCompatDelegate.setDefaultNightMode(
            when (mode) {
                NightMode.YES -> {
                    PreferenceHelper.getInstance()
                        .setValue(Constants.Preference.PREF_DARK_MODE_IS_DISMISS_PROMPT, true)
                    AppCompatDelegate.MODE_NIGHT_YES
                }

                NightMode.NO -> AppCompatDelegate.MODE_NIGHT_NO
                NightMode.AUTO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }

    fun isAppDarkMode(): NightMode {
        if (PreferenceHelper.getInstance().get(Constants.Preference.PREF_DARK_MODE_APP, false))
            return NightMode.YES
        if (PreferenceHelper.getInstance().get(Constants.Preference.PREF_DARK_MODE_SYSTEM, false))
            return NightMode.AUTO
        return NightMode.NO
    }
}


