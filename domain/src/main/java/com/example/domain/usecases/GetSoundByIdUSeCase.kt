package com.example.domain.usecases

import com.example.domain.musicmodel.MusicModel
import com.example.domain.repository.SoundRepository
import javax.inject.Inject

class GetSoundByIdUSeCase @Inject constructor(
    private val soundRepository: SoundRepository
) {
    suspend fun getExecute(id:Int) : MusicModel{
        return soundRepository.getSound(id)
    }
}