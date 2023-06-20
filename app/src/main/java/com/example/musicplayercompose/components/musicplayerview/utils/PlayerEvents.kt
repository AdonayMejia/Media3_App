package com.example.musicplayercompose.components.musicplayerview.utils

sealed class PlayerEvents{
    object Backward : PlayerEvents()
    object Forward : PlayerEvents()
    object Play:PlayerEvents()
    object Stop: PlayerEvents()
    data class ChangeProgress(val newProgress:Float) : PlayerEvents()
}
