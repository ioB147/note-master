package com.sukasrana.notesapp.view.presentation.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.notemaster.view.presentation.AuthViewModel
import com.example.notemaster.view.presentation.auth.LoginScreen
import com.example.notemaster.view.presentation.navigation.Screen

fun NavGraphBuilder.loginRoute(viewModel: AuthViewModel, navController: NavController) {
    composable(route = Screen.Login.route) {
        LoginScreen(viewModel, navController)
    }
}