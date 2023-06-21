package com.example.musicplayercompose.components.musicplayerview.viewmodel

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.domain.usecases.GetSoundByIdUSeCase
import com.example.musicplayercompose.components.musicplayerview.uistate.PlayerUiState
import com.example.data.media.MediaServiceController
import com.example.data.media.MediaState
import com.example.data.media.PlayerEvents
import com.example.data.media.UiEvents
import com.example.musicplayercompose.components.musicplayerview.utils.PlayerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel @Inject constructor(
    private val getSoundByIdUSeCase: GetSoundByIdUSeCase,
    private val mediaService: MediaServiceController,
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        PlayerUiState(
            soundImage = "",
            soundName = "",
            mediaEvents = this::useEvents,
            isPlaying = false,
            durationFormat = ::formatDuration,
            duration = 0L,
            progressFloat = 0f,
            progressString = "00:00",
            preparePlayer = this::preparePlayer
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        collectMediaState()
    }

    private fun collectMediaState() {
        viewModelScope.launch {
            mediaService.mediaState.collect { mediaState ->
                when (mediaState) {
                    is MediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    MediaState.init -> _uiState.value =
                        _uiState.value.copy(mediaPlayerStatus = PlayerStatus.Initial)

                    is MediaState.Playing -> _uiState.value =
                        _uiState.value.copy(isPlaying = mediaState.isPlaying)

                    is MediaState.Progress -> calculateProgressValues(mediaState.progress)
                    is MediaState.Ready -> {
                        _uiState.value = _uiState.value.copy(
                            mediaPlayerStatus = PlayerStatus.Ready,
                            duration = mediaState.duration
                        )
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            mediaService.onPlayerEvent(PlayerEvents.Stop)
        }
    }

    private fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(ONE_MINUTE, TimeUnit.MINUTES))
        return String.format(DURATION_FORMAT, minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        val calculatedProgress =
            if (currentProgress > DEFAULT_PROGRESS_VALUE)
                (currentProgress.toFloat() / _uiState.value.duration)
            else DEFAULT_PROGRESS_PERCENTAGE

        val calculatedProgressString = formatDuration(currentProgress)
        _uiState.value = _uiState.value.copy(
            progressFloat = calculatedProgress,
            progressString = calculatedProgressString
        )
    }
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

    companion object{
        private const val DURATION_FORMAT = "%02d:%02d"
        private const val DEFAULT_PROGRESS_VALUE = 0L
        private const val DEFAULT_PROGRESS_PERCENTAGE = 0f
        private const val ONE_MINUTE = 1L
    }
}