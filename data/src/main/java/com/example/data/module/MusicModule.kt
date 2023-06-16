package com.example.data.module

import com.example.data.network.RetrofitSearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MusicModule {
    @Provides
    @Singleton
    fun createHttpClient(): OkHttpClient{

        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Token $API_KEY")
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun createRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://freesound.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
    }

    @Provides
    @Singleton
    fun createRetrofitSearch(
        retrofit: Retrofit
    ) : RetrofitSearch = retrofit.create(RetrofitSearch::class.java)

    private const val API_KEY = "QXjpjxX2UIndRl9YPQitPOAX0E3nVMUHGeAEy4lY"
}