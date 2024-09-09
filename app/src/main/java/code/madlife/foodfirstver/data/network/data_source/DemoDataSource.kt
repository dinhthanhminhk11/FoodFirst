package code.madlife.foodfirstver.data.network.data_source

import android.content.Context
import code.madlife.foodfirstver.data.network.DefaultRequest
import code.madlife.foodfirstver.data.network.service.DemoService
import javax.inject.Inject

class DemoDataSource @Inject constructor(
    private val demoService: DemoService,
    context: Context
): BaseDataSource(context) {
    suspend fun getDemo() = safeApiCall{
        demoService.getDemo(DefaultRequest())
    }
}