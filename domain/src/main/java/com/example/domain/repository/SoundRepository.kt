package com.example.domain.repository

import com.example.domain.musicmodel.MusicModel

interface SoundRepository {
    suspend fun searchSounds(
        query:String,
        page:Int,
        pageSize:Int,
    ) : List<MusicModel>
}