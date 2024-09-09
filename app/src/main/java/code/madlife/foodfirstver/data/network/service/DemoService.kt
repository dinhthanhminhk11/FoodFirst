package code.madlife.foodfirstver.data.network.service

import code.madlife.foodfirstver.data.model.response.demo.DemoReponse
import code.madlife.foodfirstver.data.network.DefaultRequest
import code.madlife.foodfirstver.data.network.Endpoint
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface DemoService {
    @GET(Endpoint.DEMO)
    suspend fun getDemo(@Body body: DefaultRequest): Response<DemoReponse>
}