package code.madlife.foodfirstver.presentation.activity.splash

import androidx.lifecycle.MutableLiveData
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
//
//@HiltViewModel
//class SplashViewModel @Inject constructor(private val repo: SplashRepository) : BaseViewModel() {
////    private var _state = MutableLiveData<SplashState>()
////    val state = _state
//
//    fun getConfigApp() {
////        viewModelScope.launch {
////            repo.getConfigApp().collect {
////                _state.value = SplashState(it.data, SplashState.Status.GET_CONFIG_LOADING)
////                if (it.data != null) {
////                    _state.value = SplashState(it.data, SplashState.Status.GET_CONFIG_SUCCESS)
////                } else {
////                    _state.value = SplashState(null, SplashState.Status.GET_CONFIG_FAIL)
////                }
////            }
////        }
//    }
//}