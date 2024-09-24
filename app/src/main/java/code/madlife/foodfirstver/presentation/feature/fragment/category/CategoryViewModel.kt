package code.madlife.foodfirstver.presentation.feature.fragment.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import code.madlife.foodfirstver.data.CategoryRepository
import code.madlife.foodfirstver.data.network.Resource
import code.madlife.foodfirstver.presentation.core.base.BaseViewModel
import code.madlife.foodfirstver.presentation.feature.fragment.user.login.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    BaseViewModel() {
    val categoryMutableLiveData = MutableLiveData<CategoryState<List<CategoryRepository>>>()

    init {
        getListCategory()
    }

    private fun getListCategory() {
        viewModelScope.launch {
            categoryMutableLiveData.value = CategoryState.Loading
            categoryRepository.getAllCategory().collect {
                if (it.data != null && it.status == Resource.Status.SUCCESS) {
                    categoryMutableLiveData.value = CategoryState.Success(it.data.result)
                } else {
                    categoryMutableLiveData.value = CategoryState.Fail(it.message)
                }
            }
        }
    }

}