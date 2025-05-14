package com.example.notemaster.data.remote

import com.example.notemaster.data.local.entity.NotesEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val notesCollection
        get() = firestore.collection("users")
            .document(auth.currentUser?.uid ?: throw IllegalStateException("User not logged in"))
            .collection("notes")

    suspend fun getAllNotes(): List<NotesEntity> {
        val snapshot = notesCollection.get().await()
        return snapshot.documents.mapNotNull { doc ->
            try {
                NotesEntity(
                    notesId = doc.getLong("notesId")?.toInt(),
                    title = doc.getString("title") ?: "",
                    description = doc.getString("description") ?: "",
                    dueDate = doc.getLong("dueDate") ?: 0L
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun upsertNote(note: NotesEntity) {
        val noteMap = mapOf(
            "notesId" to note.notesId,
            "title" to note.title,
            "description" to note.description,
            "dueDate" to note.dueDate
        )
        if (note.notesId == null) {
            // New note: Generate a Firestore document ID
            val newDocRef = notesCollection.document()
            newDocRef.set(noteMap.plus("notesId" to newDocRef.id.hashCode())).await()
        } else {
            // Update existing note
            notesCollection.document(note.notesId.toString()).set(noteMap).await()
        }
    }

    suspend fun deleteNote(notesId: Int) {
        notesCollection.document(notesId.toString()).delete().await()
    }
}