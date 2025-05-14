package com.example.notemaster.view.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.domain.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnGetNotesById -> loadNote(event.id)
            is NotesEvent.OnTitleChange -> _state.update { it.copy(title = event.title) }
            is NotesEvent.OnDescriptionChange -> _state.update { it.copy(description = event.description) }
        }
    }

    private fun loadNote(id: Int) {
        if (id == 0) {
            // New note
            _state.update {
                it.copy(
                    currentNotesId = null,
                    title = "",
                    description = "",
                    dueDate = null
                )
            }
        } else {
            // Existing note
            viewModelScope.launch(Dispatchers.IO) {
                notesRepository.getNotesById(id).collect { note ->
                    if (note != null) {
                        _state.update {
                            it.copy(
                                currentNotesId = note.notesId,
                                title = note.title,
                                description = note.description,
                                dueDate = note.dueDate
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveNotes(note: NotesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isSaving = true, saveError = null) }
            try {
                if (note.title.isBlank() || note.description.isBlank()) {
                    _state.update { it.copy(isSaving = false, saveError = "Title and description are required") }
                    return@launch
                }
                notesRepository.upsertNotes(note)
                _state.update { it.copy(isSaving = false) }
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, saveError = "Failed to save note: ${e.message}") }
            }
        }
    }

    fun deleteNotes(notesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesRepository.deleteNotes(notesId)
            } catch (e: Exception) {
                _state.update { it.copy(syncError = "Failed to delete note: ${e.message}") }
            }
        }
    }

    fun clearSyncError() {
        _state.update { it.copy(syncError = null) }
    }

    fun clearSaveError() {
        _state.update { it.copy(saveError = null) }
    }
}