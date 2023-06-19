package com.example.data.mapper

import com.example.data.network.ApiResult
import com.example.domain.musicmodel.Images
import com.example.domain.musicmodel.MusicModel
import com.example.domain.musicmodel.Previews

fun ApiResult.toMusicModel():MusicModel {
    return MusicModel(
        id = id,
        name = name,
        images = Images(images.waveform),
        preview = Previews(previews.previewHqMp3)
    )
}