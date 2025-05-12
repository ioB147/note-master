package com.example.notemaster.view.presentation.notes

data class NotesState(
    val title: String = "",
    val description: String = "",
    val isDatePickerDialogOpen: Boolean = false,
    val dueDate: Long? = null,
    val currentNotesId: Int? = null,
)