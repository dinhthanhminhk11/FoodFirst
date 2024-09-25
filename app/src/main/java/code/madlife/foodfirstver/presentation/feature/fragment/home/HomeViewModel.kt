package code.madlife.foodfirstver.presentation.feature.fragment.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import code.madlife.foodfirstver.data.RestaurantRepository
import code.madlife.foodfirstver.data.model.Result
import code.madlife.foodfirstver.data.network.Resource
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import code.madlife.foodfirstver.presentation.feature.fragment.category.CategoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val restaurantRepository: RestaurantRepository) :
    BaseViewModel() {
    val listPostHomeMutableLiveData = MutableLiveData<RestaurantState<List<Result>>>()

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
}