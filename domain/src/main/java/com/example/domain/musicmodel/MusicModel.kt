package com.example.domain.musicmodel

data class MusicModel(
    val id: Int,
    val name:String,
    val images:Images,
    val preview:Previews
)

data class Images(
    val waveform:String
)

data class Previews(
    val previewHq:String
)
