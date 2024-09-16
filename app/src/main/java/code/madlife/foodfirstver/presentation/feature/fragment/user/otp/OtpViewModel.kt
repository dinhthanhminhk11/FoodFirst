package code.madlife.foodfirstver.presentation.feature.fragment.user.otp

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
class OtpViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    val resentOtpMutableLiveData = MutableLiveData<AuthState>()
    val verifyOtpMutableLiveData = MutableLiveData<AuthState>()

    fun verifyOtp(reqLogin: REQLogin) {
        viewModelScope.launch {
            authRepository.verifyOtp(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    verifyOtpMutableLiveData.value = AuthState.Success(it.data)
                } else {
                    verifyOtpMutableLiveData.value = AuthState.Fail(it.data?.error)
                }
            }
        }
    }

    fun reSentOtp(reqLogin: REQLogin){
        viewModelScope.launch {
            authRepository.reSentOtp(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    resentOtpMutableLiveData.value = AuthState.Success(it.data)
                } else {
                    resentOtpMutableLiveData.value = AuthState.Fail(it.data?.error)
                }
            }
        }
    }
}