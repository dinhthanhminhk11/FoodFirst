package code.madlife.foodfirstver.presentation.feature.fragment.user.login

import code.madlife.foodfirstver.data.model.response.auth.LoginResponseNative

sealed class AuthState private constructor() {
    class Success(val data: LoginResponseNative) : AuthState()
    object Loading : AuthState()
    class Fail(val message: String? = "Có lỗi xảy ra", val code: Int?) : AuthState()
}