package com.example.data.media

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MediaServiceController @Inject constructor(
    private val exoPlayer: ExoPlayer
) : Player.Listener {

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.init)

    val mediaState = _mediaState.asStateFlow()

    private var job: Job? = null

    init {
        exoPlayer.addListener(this)
        job = Job()
    }

    fun addPlayerItem(mediaItem: MediaItem) {
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    suspend fun onPlayerEvent(playerEvent: PlayerEvents) {
        when (playerEvent) {
            PlayerEvents.Backward -> seekPlayer(exoPlayer, SEEK_SECONDS, false)
            PlayerEvents.Forward -> seekPlayer(exoPlayer, SEEK_SECONDS, true)
            PlayerEvents.Play -> {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    stopProgressUpdate()
                } else {
                    exoPlayer.play()
                    _mediaState.value = MediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }

            PlayerEvents.Stop -> stopProgressUpdate()
            is PlayerEvents.ChangeProgress ->
                exoPlayer.seekTo((exoPlayer.duration * playerEvent.newProgress).toLong())
        }
    }

    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _mediaState.value =
                MediaState.Buffering(exoPlayer.currentPosition)

            ExoPlayer.STATE_READY -> _mediaState.value =
                MediaState.Ready(exoPlayer.duration)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _mediaState.value = MediaState.Playing(isPlaying = isPlaying)
        if (isPlaying) {
            GlobalScope.launch(Dispatchers.Main) {
                startProgressUpdate()
            }
        } else {
            stopProgressUpdate()
        }
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _mediaState.value = MediaState.Progress(exoPlayer.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _mediaState.value = MediaState.Playing(isPlaying = false)
    }

    private fun seekPlayer(exoPlayer: ExoPlayer, seconds: Long, isSkipped: Boolean) {
        val time = TimeUnit.SECONDS.toMillis(seconds)
        val newPosition = if (isSkipped) {
            (exoPlayer.currentPosition + time).coerceAtMost(exoPlayer.duration)
        } else {
            (exoPlayer.currentPosition - time).coerceAtLeast(MINIMUM_VALUE)
        }
        exoPlayer.seekTo(newPosition)
    }

    companion object {
        private const val SEEK_SECONDS = 15L
        private const val MINIMUM_VALUE = 0L
    }
}
