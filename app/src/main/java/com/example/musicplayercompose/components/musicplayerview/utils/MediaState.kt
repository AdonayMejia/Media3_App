package com.example.musicplayercompose.components.musicplayerview.utils

sealed class MediaState{
    object init:MediaState()
    data class Ready(val duration: Long) : MediaState()
    data class Progress(val progress: Long) : MediaState()
    data class Buffering(val progress: Long) : MediaState()
    data class Playing(val isPlaying: Boolean) : MediaState()
}
