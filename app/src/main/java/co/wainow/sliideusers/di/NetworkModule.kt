package co.wainow.sliideusers.di

import co.wainow.sliideusers.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {

        const val BASE_URL = "https://gorest.co.in/public/v2/"
        const val BEARER_TOKEN = "ENTER_KEY_HERE"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}