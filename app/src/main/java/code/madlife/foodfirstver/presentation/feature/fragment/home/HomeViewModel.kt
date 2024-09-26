package code.madlife.foodfirstver.presentation.feature.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import code.madlife.foodfirstver.data.RestaurantRepository
import code.madlife.foodfirstver.data.model.ItemRestaurantHome
import code.madlife.foodfirstver.data.model.Shop
import code.madlife.foodfirstver.data.model.request.home.HomeRequest
import code.madlife.foodfirstver.data.network.Resource
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val restaurantRepository: RestaurantRepository) :
    BaseViewModel() {
    val listPostHomeMutableLiveData = MutableLiveData<RestaurantState<List<ItemRestaurantHome>>>()
    val listPostHomeTypeMutableLiveData =
        MutableLiveData<RestaurantState<List<Shop>>>()

    init {
        getListPostHome()
    }

    private fun getListPostHome() {
        viewModelScope.launch {
            listPostHomeMutableLiveData.value = RestaurantState.Loading
            restaurantRepository.getListPostHome().collect {
                if (it.data != null && it.status == Resource.Status.SUCCESS) {
                    listPostHomeMutableLiveData.value = RestaurantState.Success(it.data.result)
                } else {
                    listPostHomeMutableLiveData.value = RestaurantState.Fail(it.message)
                }
            }
        }
    }

    fun getListPostHomeType(homeRequest: HomeRequest) {
        viewModelScope.launch {
            listPostHomeTypeMutableLiveData.value = RestaurantState.Loading
            restaurantRepository.getListPostHomeByType(homeRequest).collect {
                if (it.data != null && it.status == Resource.Status.SUCCESS) {
                    listPostHomeTypeMutableLiveData.value = RestaurantState.Success(it.data.result)
                } else {
                    listPostHomeTypeMutableLiveData.value = RestaurantState.Fail(it.message)
                }
            }
        }
    }
}