package code.madlife.foodfirstver.core.di

import android.content.Context
import android.os.Build
import code.madlife.foodfirstver.BuildConfig
import code.madlife.foodfirstver.core.common.Constants
import code.madlife.foodfirstver.core.common.HeaderRetrofitEnum
import code.madlife.foodfirstver.core.utils.Utility
import code.madlife.foodfirstver.data.network.service.AuthService
import code.madlife.foodfirstver.data.network.service.CategoryService
import code.madlife.foodfirstver.data.network.service.DemoService
import code.madlife.foodfirstver.data.network.service.RestaurantService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.devicerequests.internal.DeviceRequestsHelper.getDeviceInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Collections
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.text.Typography.dagger

@Module
@InstallIn(SingletonComponent::class)
class RetrofitServiceModule {


    private fun getHttpClient2(
        context: Context,
        headerRetrofitEnum: HeaderRetrofitEnum = HeaderRetrofitEnum.NONE
    ): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-protobuf")
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("xinternalgatewayauthnotrequired", "true")
                .addHeader("user-agent", "Tango-Android/8.79.1732129784 (samsung; SM-G988N) Android/12")
                .addHeader("tg-loc-country", "VN")
                .addHeader("tg-loc-language", "vi")
                .build()

            chain.proceed(request)
        }
        return OkHttpClient.Builder().also { client ->
            client.retryOnConnectionFailure(true)
            client.addInterceptor(interceptor)
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        }.build()
    }


    private fun getHttpClient(
        context: Context,
        headerRetrofitEnum: HeaderRetrofitEnum = HeaderRetrofitEnum.NONE
    ): OkHttpClient {
        val deviceId = Utility.getDeviceId(context)
        val userAgent = System.getProperty("http.agent") ?: "Unknown User-Agent"
        return OkHttpClient.Builder().also { client ->
            client.retryOnConnectionFailure(true)
            client.addInterceptor {
                val newRequest = it.request().newBuilder().apply {
                    header("Device-ID", deviceId)
                    header("Name-Phone", getDeviceInfo())
                    header("User-Agent", userAgent)
                }.build()
                it.proceed(newRequest)
            }
            if (BuildConfig.DEBUG) {
                val loggingContent = HttpLoggingInterceptor()
                loggingContent.setLevel(HttpLoggingInterceptor.Level.BODY)
                val collector = ChuckerCollector(context)
                val logging = ChuckerInterceptor.Builder(context).alwaysReadResponseBody(true)
                    .collector(collector).build()
                client.interceptors().add(logging)
                client.interceptors().add(loggingContent)
            }
            client.connectTimeout(30, TimeUnit.SECONDS)
            client.readTimeout(30, TimeUnit.SECONDS)
            client.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        }.build()
    }

    @Provides
    @Singleton
    @Named(Constants.Inject.API)
    fun provideDemoRetrofit(gson: Gson, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getHttpClient(context))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named(Constants.Inject.AUTH)
    fun provideRetrofitLogin(gson: Gson, context: Context): Retrofit {
        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-protobuf")
                .addHeader("Accept-Encoding", "gzip") //
                .addHeader("xinternalgatewayauthnotrequired", "true")
                .addHeader("user-agent", "Tango-Android/8.79.1732129784 (samsung; SM-G988N) Android/12")
                .addHeader("tg-loc-country", "VN")
                .addHeader("tg-loc-language", "vi")
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // Địa chỉ base URL
            .client(okHttpClient) // Sử dụng OkHttpClient đã cấu hình
            .addConverterFactory(ScalarsConverterFactory.create()) // Dùng ScalarsConverterFactory nếu cần
            .addConverterFactory(GsonConverterFactory.create(gson)) // Dùng GsonConverterFactory cho dữ liệu JSON
            .build()
    }

    @Provides
    @Singleton
    fun provideAppService(@Named(Constants.Inject.API) retrofit: Retrofit): DemoService =
        retrofit.create(DemoService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@Named(Constants.Inject.AUTH) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideCategoryService(@Named(Constants.Inject.AUTH) retrofit: Retrofit): CategoryService =
        retrofit.create(CategoryService::class.java)

    @Provides
    @Singleton
    fun provideRestaurantService(@Named(Constants.Inject.AUTH) retrofit: Retrofit): RestaurantService =
        retrofit.create(RestaurantService::class.java)
}