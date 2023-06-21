package com.example.domain.usecases

import com.example.domain.musicmodel.MusicModel
import com.example.domain.repository.SoundRepository
import javax.inject.Inject

class GetSoundsUseCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    suspend fun getExecute(query: String, page: Int, pageSize: Int): List<MusicModel> {
        return soundRepository.searchSounds(query, page = page, pageSize = pageSize)
    }
}
