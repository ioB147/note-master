package com.example.notemaster.data.local

import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.data.local.room.NotesDao


class LocalDataSource private constructor(private val notesDao: NotesDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(notesDao: NotesDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(notesDao)
            }
    }

    suspend fun upsertNotes(notes: NotesEntity) = notesDao.upsertNotes(notes)
    suspend fun deleteNotesById(id: Int) = notesDao.deleteNotesById(id)
    fun getNotesById(notesId: Int) = notesDao.getNotesId(notesId)
    fun getAllNotes() = notesDao.getAllNotes()
}