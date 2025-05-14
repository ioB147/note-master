package com.example.notemaster.view.presentation.notes


sealed class NotesEvent {
    data class OnGetNotesById(val id: Int) : NotesEvent()
    data class OnTitleChange(val title: String) : NotesEvent()
    data class OnDescriptionChange(val description: String) : NotesEvent()
}