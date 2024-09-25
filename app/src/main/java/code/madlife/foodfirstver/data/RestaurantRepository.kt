package code.madlife.foodfirstver.data

import code.madlife.foodfirstver.data.network.data_source.RestaurantDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RestaurantRepository @Inject constructor(private val restaurantDataSource: RestaurantDataSource) {
    suspend fun getListPostHome() = flow {
        emit(restaurantDataSource.getListPostHome())
    }.flowOn(Dispatchers.IO)
}