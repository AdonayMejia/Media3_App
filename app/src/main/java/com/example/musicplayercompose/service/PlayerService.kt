package com.example.musicplayercompose.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.data.service.NotificationManager
import com.example.musicplayercompose.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService : MediaSessionService(){

    @Inject
    lateinit var mediaSession: MediaSession

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    @ApplicationContext
    lateinit var context: Context

    @RequiresApi(Build.VERSION_CODES.O)
    @UnstableApi
    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            MAIN_INTENT_REQUEST_CODE,
            mainIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        notificationManager.startNotificationService(
            mediaSessionService = this,
            mediaSession = mediaSession,
            pendingIntent = pendingIntent
        )

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            release()
            if (player.playbackState != Player.STATE_IDLE) {
                player.seekTo(INITIAL_POSITION_SEEKBAR)
                player.playWhenReady = false
                player.stop()
            }
        }
    }

    override fun onGetSession(
        controllerInfo: MediaSession.ControllerInfo
    ): MediaSession = mediaSession


    companion object {
        private const val INITIAL_POSITION_SEEKBAR = 0L
        private const val MAIN_INTENT_REQUEST_CODE = 0
    }
}