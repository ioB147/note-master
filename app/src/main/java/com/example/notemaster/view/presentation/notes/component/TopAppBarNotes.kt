package com.example.notemaster.view.presentation.notes.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarNotes(
    isNotesExist: Boolean,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
    isSaving: Boolean
) {
    TopAppBar(
        title = { Text("Note") },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            if (isNotesExist) {
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            IconButton(
                onClick = onSaveButtonClick,
                enabled = !isSaving
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
    )
}