package com.example.notemaster.view.presentation.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object SignUp: Screen("signup")
    data object Splash : Screen("splash")
}