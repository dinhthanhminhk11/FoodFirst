package code.madlife.foodfirstver.data.network.service

import code.madlife.foodfirstver.data.model.ItemRestaurantHome
import code.madlife.foodfirstver.data.model.Shop
import code.madlife.foodfirstver.data.model.request.home.HomeRequest
import code.madlife.foodfirstver.data.network.ApiResponseResult
import code.madlife.foodfirstver.data.network.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestaurantService {
    @GET(Endpoint.GET_LIST__RESTAURANT_HOME)
    suspend fun getListPostHome(): Response<ApiResponseResult<List<ItemRestaurantHome>>>

    @POST(Endpoint.GET_LIST__RESTAURANT_HOME_TYPE)
    suspend fun getListPostHomeByType(@Body homeRequest: HomeRequest): Response<ApiResponseResult<List<Shop>>>
}