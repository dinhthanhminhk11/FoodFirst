package code.madlife.foodfirstver.data.network.service

import code.madlife.foodfirstver.data.model.response.CategoryResponse
import code.madlife.foodfirstver.data.network.ApiResponseResult
import code.madlife.foodfirstver.data.network.Endpoint
import retrofit2.Response
import retrofit2.http.GET

interface CategoryService {
    @GET(Endpoint.GET_LIST_CATEGORY_RESTAURANT)
    suspend fun getListCategory(): Response<ApiResponseResult<List<CategoryResponse>>>
}