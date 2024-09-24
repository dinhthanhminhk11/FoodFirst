package code.madlife.foodfirstver.data.network.data_source

import android.content.Context
import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.data.network.ApiResponseResult
import code.madlife.foodfirstver.data.network.Resource
import code.madlife.foodfirstver.data.network.service.CategoryService
import retrofit2.Response
import javax.inject.Inject

class CategoryDataSource @Inject constructor(
    private val categoryService: CategoryService,
    context: Context
) :
    BaseDataSource(context) {
    suspend fun getAllCategory() = safeApiCall {
        categoryService.getListCategory()
    }
}