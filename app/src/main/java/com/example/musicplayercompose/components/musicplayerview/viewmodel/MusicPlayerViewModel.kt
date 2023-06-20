package com.example.musicplayercompose.components.musicplayerview.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.TrackSelector
import com.example.domain.musicmodel.MusicModel
import com.example.domain.usecases.GetSoundByIdUSeCase
import com.example.domain.usecases.GetSoundsUseCase
import com.example.musicplayercompose.components.musicplayerview.PlayerUiState
import com.example.musicplayercompose.components.musicplayerview.utils.MediaServiceController
import com.example.musicplayercompose.components.musicplayerview.utils.PlayerEvents
import com.example.musicplayercompose.components.musicplayerview.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val getSoundByIdUSeCase: GetSoundByIdUSeCase,
    private val mediaService:MediaServiceController,
) : ViewModel() {

    private val _sliderPosition = MutableStateFlow(sliderPositionValue)
    val sliderPosition = _sliderPosition.asStateFlow()

    private val _uiState = MutableStateFlow(
        PlayerUiState(
            soundImage = "",
            soundName = "",
            mediaEvents = this::useEvents,
            isPlaying = false,
            updateBar = this::updatePosition
        )
    )
    val uiState = _uiState.asStateFlow()

      @SuppressLint("SuspiciousIndentation")
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
                    mediaService.addPlayerItem(mediaItem)
                _uiState.value= _uiState.value.copy(
                    soundImage = response.images.waveform,
                    soundName = response.name
                )
            }.onFailure { e -> Log.d("Error", "${e.message}") }
        }
     }

    private fun useEvents(events: UiEvents){
        viewModelScope.launch {
            when(events){
                UiEvents.Play -> mediaService.onPlayerEvent(PlayerEvents.Play)
                UiEvents.Forward -> mediaService.onPlayerEvent(PlayerEvents.Forward)
                UiEvents.Backward -> mediaService.onPlayerEvent(PlayerEvents.Backward)
            }
        }
    }

    private fun updatePosition(isPlaying:Boolean){
        viewModelScope.launch {
            mediaService.onIsPlayingChanged(isPlaying)
        }
    }
    companion object{
        const val sliderPositionValue = 0f

    }
}