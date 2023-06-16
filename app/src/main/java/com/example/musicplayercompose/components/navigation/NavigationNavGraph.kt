package com.example.musicplayercompose.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicplayercompose.components.musiclistview.MusicListScreen
import com.example.musicplayercompose.components.musicplayerview.MusicPlayerScreen

@Composable
fun NavGraph(
    navController:NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "MusicListScreen",
        ) {
        composable( route = "MusicListScreen"){
            MusicListScreen()
        }
        composable( route = "MusicPlayerScreen"){
            MusicPlayerScreen()
        }
    }
}