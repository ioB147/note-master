package com.example.notemaster.domain.repository

import com.example.notemaster.data.local.entity.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun upsertNotes(notesEntity: NotesEntity)

    suspend fun deleteNotes(notesId: Int)

    fun getNotesById(notesId: Int): Flow<NotesEntity?>

    fun getAllNotes(): Flow<List<NotesEntity>>
}