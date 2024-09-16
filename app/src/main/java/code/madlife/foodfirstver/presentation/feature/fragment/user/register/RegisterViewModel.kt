package code.madlife.foodfirstver.presentation.feature.fragment.user.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import code.madlife.foodfirstver.data.AuthRepository
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel@Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    val authState = MutableLiveData<AuthState>()

    fun register(reqLogin: REQLogin) {
        viewModelScope.launch {
            authRepository.register(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    authState.value = AuthState.Success(it.data)
                } else {
                    authState.value = AuthState.Fail(it.data?.error)
                }
            }
        }
    }
}