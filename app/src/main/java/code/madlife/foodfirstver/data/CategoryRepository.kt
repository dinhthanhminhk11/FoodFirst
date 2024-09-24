package code.madlife.foodfirstver.data

import code.madlife.foodfirstver.data.network.data_source.CategoryDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDataSource: CategoryDataSource) {
    suspend fun getAllCategory() = flow {
        emit(categoryDataSource.getAllCategory())
    }.flowOn(Dispatchers.IO)
}