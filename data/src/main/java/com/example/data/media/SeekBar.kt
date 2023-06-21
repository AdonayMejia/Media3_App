package com.example.data.media

import androidx.media3.common.Player
import java.util.concurrent.TimeUnit

private const val MINIMUM_VALUE = 0L
fun seekPlayer(player: Player, seconds: Long, isForward: Boolean) {
    val timeMillis = TimeUnit.SECONDS.toMillis(seconds)
    val newPosition = if (isForward) {
        (player.currentPosition + timeMillis).coerceAtMost(player.duration)
    } else {
        (player.currentPosition - timeMillis).coerceAtLeast(MINIMUM_VALUE)
    }
    player.seekTo(newPosition)
}