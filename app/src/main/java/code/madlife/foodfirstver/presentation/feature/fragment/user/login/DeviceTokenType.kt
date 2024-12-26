package code.madlife.foodfirstver.presentation.feature.fragment.user.login

class DeviceTokenType private constructor(
    val typeName: String,
    val ordinal: Int,
    val typeCode: Int
) {

    companion object {
        val UNKNOWN = DeviceTokenType("DEVICE_TOKEN_UNKNOWN", 0, -1)
        val TANGO = DeviceTokenType("DEVICE_TOKEN_TANGO", 1, 0)
        val IPHONE = DeviceTokenType("DEVICE_TOKEN_IPHONE", 2, 1)
        val ANDROID = DeviceTokenType("DEVICE_TOKEN_ANDROID", 3, 2)
        val WINPHONE = DeviceTokenType("DEVICE_TOKEN_WINPHONE", 4, 3)
        val GCM = DeviceTokenType("DEVICE_TOKEN_GCM", 5, 6)
        val APPLE_VOIP = DeviceTokenType("DEVICE_TOKEN_APPLE_VOIP", 6, 8)

        private val allTypes = listOf(UNKNOWN, TANGO, IPHONE, ANDROID, WINPHONE, GCM, APPLE_VOIP)

        fun valueOf(name: String): DeviceTokenType {
            return allTypes.find { it.typeName == name }
                ?: throw IllegalArgumentException("No enum constant with name $name")
        }

        fun values(): List<DeviceTokenType> {
            return allTypes
        }
    }

    fun getTypeCode(): Int {
        return typeCode
    }
}