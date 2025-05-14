package com.example.notemaster.data.repository

import com.example.notemaster.data.local.LocalDataSource
import com.example.notemaster.data.local.entity.NotesEntity
import com.example.notemaster.data.remote.FirebaseDataSource
import com.example.notemaster.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val firebaseDataSource: FirebaseDataSource
) : NotesRepository {

    override suspend fun upsertNotes(notesEntity: NotesEntity) {
        localDataSource.upsertNotes(notesEntity)
        firebaseDataSource.upsertNote(notesEntity)
    }

    override suspend fun deleteNotes(notesId: Int) {
        localDataSource.deleteNotesById(notesId)
        firebaseDataSource.deleteNote(notesId)
    }

    override fun getNotesById(notesId: Int): Flow<NotesEntity?> {
        return localDataSource.getNotesById(notesId)
    }

    override fun getAllNotes(): Flow<List<NotesEntity>> {
        return localDataSource.getAllNotes()
    }

    override suspend fun syncNotesWithFirebase() {
        val localNotes = localDataSource.getAllNotesSync()
        val remoteNotes = firebaseDataSource.getAllNotes()

        val localNotesMap = localNotes.associateBy { it.notesId }
        val remoteNotesMap = remoteNotes.associateBy { it.notesId }

        remoteNotes.forEach { remoteNote ->
            val localNote = localNotesMap[remoteNote.notesId]
            if (localNote == null || (remoteNote.dueDate > localNote.dueDate)) {
                localDataSource.upsertNotes(remoteNote)
            }
        }

        localNotes.forEach { localNote ->
            val remoteNote = remoteNotesMap[localNote.notesId]
            if (remoteNote == null || (localNote.dueDate > remoteNote.dueDate)) {
                firebaseDataSource.upsertNote(localNote)
            }
        }

        localNotes.forEach { localNote ->
            if (remoteNotesMap[localNote.notesId] == null) {
                localDataSource.deleteNotesById(localNote.notesId!!)
            }
        }

        // Delete remote notes not in local
        remoteNotes.forEach { remoteNote ->
            if (localNotesMap[remoteNote.notesId] == null) {
                firebaseDataSource.deleteNote(remoteNote.notesId!!)
            }
        }
    }
}