package com.example.notemaster.view.presentation.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object SignUp: Screen("signup")
    data object Splash : Screen("splash")
    data object Home: Screen("home")
    data object Notes: Screen("notes/{id}"){
        fun createRoute(id: Int?) = "notes/$id"
    }
    data object User: Screen("user")
}