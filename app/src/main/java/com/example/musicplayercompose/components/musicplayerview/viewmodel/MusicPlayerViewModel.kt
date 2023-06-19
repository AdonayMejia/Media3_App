package com.example.musicplayercompose.components.musicplayerview.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.trackselection.TrackSelector
import com.example.domain.musicmodel.MusicModel
import com.example.domain.usecases.GetSoundByIdUSeCase
import com.example.domain.usecases.GetSoundsUseCase
import com.example.musicplayercompose.components.musicplayerview.PlayerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val getSoundByIdUSeCase: GetSoundByIdUSeCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        PlayerUiState(
            soundImage = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    var player:Player? = null
      fun preparePlayer(id:Int){
        viewModelScope.launch {
            runCatching {
                val response = getSoundByIdUSeCase.getExecute(id)
                val mediaItem = MediaItem.Builder()
                    .setUri(response.preview.previewHq)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(response.name)
                            .setArtworkUri(Uri.parse(response.images.waveform))
                            .build()
                    ).build()
                player?.addMediaItem(mediaItem)
                player?.release()
                player?.play()
                _uiState.value= _uiState.value.copy(
                    soundImage = response.images.waveform
                )
            }.onFailure { e -> Log.d("Error", "${e.message}") }
        }
         Log.wtf("Sound2","$id")
     }

}