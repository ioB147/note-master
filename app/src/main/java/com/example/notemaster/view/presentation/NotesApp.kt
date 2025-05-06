package com.example.notemaster.view.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notemaster.view.presentation.navigation.NotesNavGraph


@Composable
fun NotesApp(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NotesNavGraph( viewModel = viewModel,navController = navController, modifier = modifier)
}