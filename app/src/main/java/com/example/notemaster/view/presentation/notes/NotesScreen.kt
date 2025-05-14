package com.example.notemaster.view.presentation.notes

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.utils.Converter.changeMillisToDateString
import com.example.notemaster.view.presentation.notes.component.TopAppBarNotes
import java.time.Instant

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    id: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val notesViewModel: NotesViewModel = hiltViewModel()
    val state by notesViewModel.state.collectAsStateWithLifecycle(lifecycleOwner = lifecycleOwner)

    // Show sync error toast
    state.syncError?.let { error ->
        LaunchedEffect(error) {
            Toast.makeText(context, "Sync failed: $error", Toast.LENGTH_SHORT).show()
            notesViewModel.clearSyncError()
        }
    }

    // Show save error toast
    state.saveError?.let { error ->
        LaunchedEffect(error) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            notesViewModel.clearSaveError()
        }
    }

    LaunchedEffect(id) {
        notesViewModel.onEvent(NotesEvent.OnGetNotesById(id))
    }

    NotesContent(
        isNotesExist = state.currentNotesId != null,
        onBackClick = { navController.navigateUp() },
        onDeleteClick = {
            state.currentNotesId?.let { notesViewModel.deleteNotes(it) }
            navController.navigateUp()
        },
        title = state.title,
        onTitleChange = { notesViewModel.onEvent(NotesEvent.OnTitleChange(it)) },
        description = state.description,
        onDescriptionChange = { notesViewModel.onEvent(NotesEvent.OnDescriptionChange(it)) },
        dueDate = state.dueDate,
        isSaving = state.isSaving,
        onSaveClick = {
            val notes = NotesEntity(
                notesId = state.currentNotesId,
                title = state.title,
                description = state.description,
                dueDate = state.dueDate ?: Instant.now().toEpochMilli()
            )
            notesViewModel.saveNotes(notes)
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesContent(
    isNotesExist: Boolean,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    dueDate: Long?,
    isSaving: Boolean,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBarNotes(
                isNotesExist = isNotesExist,
                onBackButtonClick = onBackClick,
                onDeleteButtonClick = onDeleteClick,
                onSaveButtonClick = onSaveClick,
                isSaving = isSaving
            )
        },
        modifier = modifier
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dueDate?.changeMillisToDateString() ?: "No due date",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text(text = "Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.padding(4.dp))
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text(text = "Description") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent
                )
            )
        }
    }
}