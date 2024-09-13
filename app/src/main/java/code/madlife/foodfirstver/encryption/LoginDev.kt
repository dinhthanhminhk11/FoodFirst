package code.madlife.foodfirstver.encryption

class LoginDev {
    init {
        Login.load()
    }

    companion object {
        @JvmStatic
        external fun encryptKey(input: String): String
    }
}
