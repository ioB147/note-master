package com.example.notemaster.view.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.notemaster.view.presentation.navigation.NotesNavGraph


@Composable
fun NotesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NotesNavGraph(navController = navController, modifier = modifier)
}