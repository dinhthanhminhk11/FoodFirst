package code.madlife.foodfirstver.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PreferenceHelper {

    var preferences: SharedPreferences? = null
    private val gson = Gson()
    fun init(context: Context) {
        preferences = context.getSharedPreferences("base.mvvm", Context.MODE_PRIVATE)

    }

    companion object {
        fun getInstance(): PreferenceHelper = PreferenceHolder.preferenceHelper
    }

    private object PreferenceHolder {
        val preferenceHelper = PreferenceHelper()
    }


    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun SharedPreferences.clean() {
        val editor = this.edit()
        editor.clear()
        editor.apply()
    }


    fun setValue(key: String, value: Any?) {
        preferences?.apply {
            when (value) {
                is String -> edit { it.putString(key, value) }
                is Int -> edit { it.putInt(key, value) }
                is Boolean -> edit { it.putBoolean(key, value) }
                is Float -> edit { it.putFloat(key, value) }
                is Long -> edit { it.putLong(key, value) }
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
    }

    inline fun <reified T : Any> get(key: String, defaultValue: T): T {
        preferences?.apply {
            return when (T::class) {
                String::class -> getString(key, defaultValue as String) as T
                Int::class -> getInt(key, defaultValue as Int) as T
                Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
                Float::class -> getFloat(key, defaultValue as Float) as T
                Long::class -> getLong(key, defaultValue as Long) as T
                else -> throw UnsupportedOperationException("Not yet implemented")
            }
        }
        return defaultValue
    }

    fun remove(key: String) {
        preferences?.edit()?.remove(key)?.apply()
    }
}