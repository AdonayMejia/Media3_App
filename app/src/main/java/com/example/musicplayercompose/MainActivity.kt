package com.example.musicplayercompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.musicplayercompose.components.MainScreen
import com.example.musicplayercompose.service.PlayerService
import com.example.musicplayercompose.ui.theme.MusicPlayerComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, PlayerService::class.java)
        startForegroundService(intent)

        setContent {
            MusicPlayerComposeTheme {
                MainScreen()
            }
        }
    }
}
