package com.example.data.media

sealed class UiEvents {
    object Backward : UiEvents()
    object Forward : UiEvents()
    object Play : UiEvents()
}
