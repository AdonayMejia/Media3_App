package com.example.musicplayercompose.components.musicplayerview.utils

sealed class PlayerStatus{
    object Initial : PlayerStatus()
    object Ready : PlayerStatus()
}
