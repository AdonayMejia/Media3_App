package com.example.data.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AdapterNotifications @Inject constructor(
    @ApplicationContext private val context: Context,
    private val exoPlayer: ExoPlayer
) {
    private var notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)

    init {
        createsNotificationChannel()
    }

    private fun createsNotificationChannel(){
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    companion object{
        private const val NOTIFICATION_ID = 169
        private const val CHANNEL_NAME = "channel 1"
        private const val CHANNEL_ID = "channel id 1"
    }
}