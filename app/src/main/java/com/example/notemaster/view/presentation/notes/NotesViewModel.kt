package com.example.notemaster.view.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.domain.repository.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val taskRepository: NotesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state = _state.asStateFlow()

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.OnDateChange -> _state.update {
                it.copy(
                    dueDate = event.date
                )
            }

            is NotesEvent.OnDescriptionChange -> _state.update {
                it.copy(
                    description = event.description
                )
            }


            is NotesEvent.OnTitleChange -> _state.update {
                it.copy(
                    title = event.title
                )
            }

            is NotesEvent.OnGetNotesById -> fetchNotes(event.id)
        }
    }

    fun saveNotes(notesEntity: NotesEntity) = viewModelScope.launch {
        taskRepository.upsertNotes(
            notesEntity = notesEntity
        )
    }

    fun deleteNotes(notesId: Int) = viewModelScope.launch {
        taskRepository.deleteNotes(notesId)
    }

    private fun fetchNotes(notesId: Int) = viewModelScope.launch {
        taskRepository.getNotesById(notesId).collect { notes ->
            _state.update {
                it.copy(
                    currentNotesId = notes?.notesId,
                    title = notes?.title ?: "",
                    description = notes?.description ?: "",
                    dueDate = notes?.dueDate,
                )
            }
        }
    }
}