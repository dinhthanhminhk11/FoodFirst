package code.madlife.foodfirstver.data.network.service

import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.model.response.auth.LoginResponseNative
import code.madlife.foodfirstver.data.network.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST(Endpoint.LOGIN)
    suspend fun login(
        @Body reqLogin: REQLogin
    ): Response<LoginResponseNative>
}