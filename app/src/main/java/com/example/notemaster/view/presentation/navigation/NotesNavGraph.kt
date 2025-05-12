package com.example.notemaster.view.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.notemaster.view.presentation.AuthViewModel
import com.sukasrana.notesapp.view.presentation.auth.loginRoute
import com.example.notemaster.view.presentation.auth.signUpRoute
import com.example.notemaster.view.presentation.home.homeScreenRoute
import com.example.notemaster.view.presentation.notes.notesScreenRoute
import com.example.notemaster.view.presentation.splash.splashScreenRoute

@Composable
fun NotesNavGraph(
    viewModel: AuthViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        loginRoute(viewModel,navController)
        signUpRoute(viewModel, navController)
        splashScreenRoute(navController)
        homeScreenRoute(viewModel,navController)
        notesScreenRoute(navController)
    }
}