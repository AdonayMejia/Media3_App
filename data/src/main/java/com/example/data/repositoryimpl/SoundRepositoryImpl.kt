package com.example.data.repositoryimpl

import android.util.Log
import com.example.data.mapper.toMusicModel
import com.example.data.network.RetrofitSearch
import com.example.domain.musicmodel.MusicModel
import com.example.domain.repository.SoundRepository
import javax.inject.Inject

class SoundRepositoryImpl @Inject constructor(
    private val apiService: RetrofitSearch
) : SoundRepository {
    override suspend fun searchSounds(query: String, page: Int, pageSize: Int): List<MusicModel> {
        if (query.isEmpty()){
            return emptyList()
        }
        val response = apiService.searchSounds(query = query, page = page.toString(), pageSize = pageSize.toString())
        Log.wtf("ItWorks","$response")
        return response.results.map {
            it.toMusicModel()
        }

    }

}