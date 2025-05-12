package com.example.notemaster.view.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notemaster.view.presentation.AuthViewModel
import com.example.notemaster.view.presentation.navigation.Screen

fun NavGraphBuilder.homeScreenRoute(viewModel: AuthViewModel, navController: NavController) {
    composable(route = Screen.Home.route) {
        HomeScreen(viewModel, navController)
    }
}