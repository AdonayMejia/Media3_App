package com.example.data.network

import com.example.domain.musicmodel.MusicModel
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitSearch {
    @GET("apiv2/search/text/")
    suspend fun searchSounds(
        @Query("query") query: String,
        @Query("page") page: String,
        @Query("page_size") pageSize: String = "15",
        @Query("fields") fields: String = "id,name,previews,images"
    ): ApiResponse

    @GET("apiv2/sounds/{soundId}")
    suspend fun getSound(
        @Path("soundId") soundId:Int
    ) : ApiResult
}