package code.madlife.foodfirstver.data

import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.data.network.data_source.AuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authDataSource: AuthDataSource) {
    suspend fun login(reqLogin: REQLogin) = flow {
        emit(authDataSource.login(reqLogin))
    }.flowOn(Dispatchers.IO)

    suspend fun register(reqLogin: REQLogin) = flow {
        emit(authDataSource.register(reqLogin))
    }.flowOn(Dispatchers.IO)

    suspend fun verifyOtp(reqLogin: REQLogin) = flow {
        emit(authDataSource.verifyOtp(reqLogin))
    }.flowOn(Dispatchers.IO)

    suspend fun reSentOtp(reqLogin: REQLogin) = flow {
        emit(authDataSource.reSentOtp(reqLogin))
    }.flowOn(Dispatchers.IO)
    suspend fun setPassword(reqLogin: REQLogin) = flow {
        emit(authDataSource.setPassword(reqLogin))
    }.flowOn(Dispatchers.IO)
}