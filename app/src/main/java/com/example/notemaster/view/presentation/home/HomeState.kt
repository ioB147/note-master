package com.example.notemaster.view.presentation.home

import com.example.notemaster.data.local.entity.NotesEntity

data class HomeState(
    val notes: List<NotesEntity> = emptyList()
)
