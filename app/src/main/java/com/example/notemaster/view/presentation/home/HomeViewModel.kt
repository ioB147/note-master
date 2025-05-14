package com.example.notemaster.view.presentation.home

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
class HomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        notesRepository.getAllNotes().collect { notes ->
            _state.update { it.copy(notes = notes) }
        }
    }

    fun syncNotesWithFirebase() = viewModelScope.launch(Dispatchers.IO) {
        _state.update { it.copy(isSyncing = true, syncError = null) }
        try {
            notesRepository.syncNotesWithFirebase()
            _state.update { it.copy(isSyncing = false) }
        } catch (e: Exception) {
            _state.update { it.copy(isSyncing = false, syncError = e.message) }
        }
    }

    fun clearSyncError() {
        _state.update { it.copy(syncError = null) }
    }
}