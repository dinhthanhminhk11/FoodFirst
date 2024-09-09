package code.madlife.foodfirstver.data

import code.madlife.foodfirstver.data.network.data_source.DemoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DemoRepository @Inject constructor(private val demoDataSource: DemoDataSource) {
    fun getDemo() = flow {
        emit(demoDataSource.getDemo())
    }.flowOn(Dispatchers.IO)
}