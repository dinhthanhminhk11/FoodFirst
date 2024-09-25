package code.madlife.foodfirstver.data.network.service

import code.madlife.foodfirstver.data.model.Result
import code.madlife.foodfirstver.data.network.ApiResponseResult
import code.madlife.foodfirstver.data.network.Endpoint
import retrofit2.Response
import retrofit2.http.GET

interface RestaurantService {
    @GET(Endpoint.GET_LIST_CATEGORY_RESTAURANT)
    suspend fun getListPostHome(): Response<ApiResponseResult<List<Result>>>
}