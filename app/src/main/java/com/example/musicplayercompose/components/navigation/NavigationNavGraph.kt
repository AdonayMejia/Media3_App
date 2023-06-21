package com.example.musicplayercompose.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicplayercompose.components.musiclistview.MusicListScreen
import com.example.musicplayercompose.components.musicplayerview.MusicPlayerScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "MusicListScreen",
    ) {
        composable(route = "MusicListScreen") {
            MusicListScreen(
                onSoundSelected = { id ->
                    navController.navigate("MusicPlayerScreen/$id")
                }
            )
        }
        composable(
            route = "MusicPlayerScreen/{soundId}",
            arguments = listOf(navArgument("soundId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("soundId")?.let { soundId ->
                MusicPlayerScreen(soundId = soundId)
            }
        }
    }
}
