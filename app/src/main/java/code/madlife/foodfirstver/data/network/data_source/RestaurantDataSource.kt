package code.madlife.foodfirstver.data.network.data_source

import android.content.Context
import code.madlife.foodfirstver.data.model.request.home.HomeRequest
import code.madlife.foodfirstver.data.network.service.CategoryService
import code.madlife.foodfirstver.data.network.service.RestaurantService
import javax.inject.Inject

class RestaurantDataSource @Inject constructor(
    private val restaurantService: RestaurantService,
    context: Context
) :
    BaseDataSource(context) {
    suspend fun getListPostHome() = safeApiCall {
        restaurantService.getListPostHome()
    }

    suspend fun getListPostHomeByType(homeRequest: HomeRequest) = safeApiCall {
        restaurantService.getListPostHomeByType(homeRequest)
    }
}

