package code.madlife.foodfirstver.presentation.feature.fragment.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import code.madlife.foodfirstver.Contact
import code.madlife.foodfirstver.data.AuthRepository
import code.madlife.foodfirstver.data.model.request.auth.REQLogin
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    val authState = MutableLiveData<AuthState>()
    val checkAccountMutableLiveData = MutableLiveData<AuthState>()


    fun fakeLogin(contact: RequestBody){
        viewModelScope.launch {
            authRepository.fakeLogin(contact).collect {
                if (it.data != null && it.data.status == true) {
                    authState.value = AuthState.Success(it.data)
                } else {
                    authState.value = AuthState.Fail(it.data?.error, it.data?.code)
                }
            }
        }
    }


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

    fun checkAccount(reqLogin: REQLogin) {
        viewModelScope.launch {
            authRepository.checkAccount(reqLogin).collect {
                if (it.data != null && it.data.status == true) {
                    checkAccountMutableLiveData.value = AuthState.Success(it.data)
                } else {
                    checkAccountMutableLiveData.value =
                        AuthState.Fail(it.data?.error, it.data?.code)
                }
            }
        }
    }
}