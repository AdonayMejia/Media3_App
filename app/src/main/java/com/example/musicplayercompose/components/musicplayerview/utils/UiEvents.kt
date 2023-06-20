package com.example.musicplayercompose.components.musicplayerview.utils

sealed class UiEvents{
    object Backward : UiEvents()
    object Forward : UiEvents()
    object Play:UiEvents()
}
