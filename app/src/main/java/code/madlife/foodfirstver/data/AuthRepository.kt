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
}