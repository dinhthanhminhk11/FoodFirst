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
}