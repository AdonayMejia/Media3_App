package com.example.musicplayercompose.components

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.musicplayercompose.components.navigation.NavGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold {
        NavGraph(navController = navController)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}