package com.example.notemaster.data.local

import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.data.local.room.NotesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val notesDao: NotesDao
) {
    suspend fun upsertNotes(notesEntity: NotesEntity) {
        notesDao.upsertNotes(notesEntity)
    }

    suspend fun deleteNotesById(notesId: Int) {
        notesDao.deleteNotesById(notesId)
    }

    fun getNotesById(notesId: Int): Flow<NotesEntity?> {
        return notesDao.getNotesById(notesId)
    }

    fun getAllNotes(): Flow<List<NotesEntity>> {
        return notesDao.getAllNotes()
    }

    suspend fun getAllNotesSync(): List<NotesEntity> = withContext(Dispatchers.IO) {
        notesDao.getAllNotesSync()
    }
}