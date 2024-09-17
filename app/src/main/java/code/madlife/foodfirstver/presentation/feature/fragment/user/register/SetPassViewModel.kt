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
class SetPassViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    val setPasswordMutableLiveData = MutableLiveData<AuthState>()

    val authState = MutableLiveData<AuthState>()

    fun login(reqLogin: REQLogin) {
        viewModelScope.launch {
            authRepository.login(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    authState.value = AuthState.Success(it.data)
                } else {
                    authState.value = AuthState.Fail(it.data?.error, it.data?.code)
                }
            }
        }
    }

    fun setPassword(reqLogin: REQLogin) {
        viewModelScope.launch {
            authRepository.setPassword(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    setPasswordMutableLiveData.value = AuthState.Success(it.data)
                } else {
                    setPasswordMutableLiveData.value = AuthState.Fail(it.data?.error, it.data?.code)
                }
            }
        }
    }
}