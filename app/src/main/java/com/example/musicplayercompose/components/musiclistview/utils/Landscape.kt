package com.example.musicplayercompose.components.musiclistview.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLandscape(): Boolean {
    val orientation = LocalConfiguration.current.orientation
    return orientation == Configuration.ORIENTATION_LANDSCAPE
}