package com.example.notemaster.view.presentation.notes

data class NotesState(
    val currentNotesId: Int? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isSaving: Boolean = false,
    val saveError: String? = null,
    val syncError: String? = null
)
