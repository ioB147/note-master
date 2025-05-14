package com.example.notemaster.view.presentation.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notemaster.view.presentation.AuthViewModel
import com.example.notemaster.view.presentation.navigation.Screen

fun NavGraphBuilder.homeScreenRoute(navController: NavController) {
    composable(route = Screen.Home.route) {
        val authViewModel: AuthViewModel = hiltViewModel()
        HomeScreen(
            viewModel = authViewModel,
            navController = navController
        )
    }
}