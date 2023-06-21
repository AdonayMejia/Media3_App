package com.example.musicplayercompose.components.musicplayerview.uistate

import com.example.data.media.UiEvents
import com.example.musicplayercompose.components.musicplayerview.utils.PlayerStatus

data class PlayerUiState(
    val soundImage:String = "",
    val soundName:String = "",
    val mediaEvents:(UiEvents) -> Unit,
    val durationFormat: (Long) -> String = { "" },
    val duration: Long = 0L,
    var isPlaying: Boolean = false,
    val progressFloat: Float = 0f,
    val progressString: String = "",
    val preparePlayer:(Int) -> Unit,
    val mediaPlayerStatus: PlayerStatus = PlayerStatus.Initial,
    )
