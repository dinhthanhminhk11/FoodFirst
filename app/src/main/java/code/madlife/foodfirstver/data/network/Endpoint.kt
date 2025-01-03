package code.madlife.foodfirstver.data.network

object Endpoint {
    const val DEMO = "DEMO"
    const val LOGIN = "auth/login"
    const val REGISTER = "auth/register"
    const val VERIFY_OTP = "auth/verifyOtp"
    const val RESENT_OTP = "auth/resentOtp"
    const val SET_PASSWORD = "auth/setPassword"
    const val CHECK_ACCOUNT = "auth/checkAccount"
    const val LOGIN_BY_TOKEN = "auth/loginWithToken"
    const val FAKE_LOGIN = "auth/fakeLoginProtobuf/register.proto"
    const val GET_LIST_CATEGORY_RESTAURANT = "restaurantType/getAllRestaurantType"
    const val GET_LIST__RESTAURANT_HOME = "restaurant/listRestaurantHomePage"
    const val GET_LIST__RESTAURANT_HOME_TYPE = "restaurant/listRestaurantHomePageType"
}