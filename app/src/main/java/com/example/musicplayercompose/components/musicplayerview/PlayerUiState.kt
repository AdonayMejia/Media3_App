package com.example.musicplayercompose.components.musicplayerview

import com.example.musicplayercompose.components.musicplayerview.utils.UiEvents

data class PlayerUiState(
    val soundImage:String = "",
    val soundName:String = "",
    val mediaEvents:(UiEvents) -> Unit,
    val isPlaying:Boolean ,
    val updateBar:(Boolean) -> Unit
)
