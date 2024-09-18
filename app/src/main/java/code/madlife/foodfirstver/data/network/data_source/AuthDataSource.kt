package code.madlife.foodfirstver.data.network.data_source

import android.content.Context
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.model.response.auth.LoginResponseNative
import code.madlife.foodfirstver.data.network.Resource
import code.madlife.foodfirstver.data.network.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val authService: AuthService, context: Context) :
    BaseDataSource(context) {

    suspend fun login(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.login(reqLogin)
    }

    suspend fun register(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.register(reqLogin)
    }

    suspend fun verifyOtp(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.verifyOtp(reqLogin)
    }

    suspend fun reSentOtp(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.reSentOtp(reqLogin)
    }

    suspend fun setPassword(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.setPassword(reqLogin)
    }

    suspend fun checkAccount(reqLogin: REQLogin): Resource<LoginResponseNative> = safeApiCall {
        authService.checkAccount(reqLogin)
    }

    suspend fun loginByToken(token: String): Resource<LoginResponseNative> = safeApiCall {
        authService.loginByToken(token)
    }
}