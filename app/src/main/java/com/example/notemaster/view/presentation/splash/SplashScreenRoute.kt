package com.example.notemaster.view.presentation.splash

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notemaster.view.presentation.navigation.Screen

fun NavGraphBuilder.splashScreenRoute(navController: NavController) {
    composable(route = Screen.Splash.route) {
        SplashScreen(navController)
    }
}