package com.example.notemaster.view.presentation.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notemaster.R
import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.view.presentation.AuthViewModel
import com.example.notemaster.view.presentation.home.component.NotesCard
import com.example.notemaster.view.presentation.navigation.Screen

@Composable
fun HomeScreen(
    viewModel: AuthViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    listState: LazyListState = rememberLazyListState(),
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.state.collectAsState()

    HomeContent(
        viewModel = viewModel,
        tasks = state.notes,
        isDarkMode = isDarkMode,
        isFABExpanded = remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }.value,
        navController = navController,
        onFabClicked = {
            navController.navigate(Screen.Notes.createRoute(0))
        },
        onListClicked = {
            navController.navigate(Screen.User.route)
        },
        onMapsClicked = { /* TODO */ },
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewModel: AuthViewModel,
    tasks: List<NotesEntity>,
    isDarkMode: Boolean,
    isFABExpanded: Boolean,
    navController: NavController,
    onFabClicked: () -> Unit,
    onListClicked: () -> Unit,
    onMapsClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.state.collectAsState()
    var isVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = onMapsClicked) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location"
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                        ) {
                            IconButton(onClick = onListClicked) {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = "List Retrofit"
                                )
                            }
                            IconButton(onClick = { homeViewModel.syncNotesWithFirebase() }) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Sync Notes",
                                )
                            }
                            IconButton(
                                onClick = { /*TODO: Handle clock action */ },
                                modifier = Modifier.size(25.dp)
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.ic_oclock),
                                    contentDescription = "Clock"
                                )
                            }
                            IconButton(onClick = { isVisible = !isVisible }) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClicked,
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                contentColor = Color.White,
                containerColor = if (isDarkMode) Color(0xFF1E1E1E) else Color(0xFF6200EE),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Notes"
                )
            }
        },
        containerColor = Color.Transparent,
        modifier = modifier
    ) { contentPadding ->
        if (tasks.isEmpty())
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Image(
                    painter = painterResource(R.drawable.image_notes),
                    contentDescription = "Task Image",
                    modifier = Modifier.height(200.dp)
                )
            }
        else
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                items(tasks, key = { it.notesId ?: 0 }) {
                    NotesCard(
                        title = it.title,
                        text = it.description,
                        onItemNotesClicked = {
                            navController.navigate(
                                Screen.Notes.createRoute(it.notesId)
                            )
                        }
                    )
                }
            }
    }
    if (isVisible)
        Popup(
            offset = IntOffset(600, 80)
        ) {
            Box {
                Button(onClick = {
                    viewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }) {
                    Text(text = "LogOut")
                }
            }
        }
}