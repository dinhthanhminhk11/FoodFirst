package code.madlife.foodfirstver.encryption

import android.util.Log

class LoginDev {
    init {
        Login.load()
    }

    companion object {
        @JvmStatic
        external fun encryptKey(input: String): String
    }


}
