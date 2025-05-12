package com.example.notemaster.di

import android.content.Context
import com.example.notemaster.data.local.LocalDataSource
import com.example.notemaster.data.local.room.NotesDatabase
import com.example.notemaster.data.repository.NotesRepositoryImpl
import com.example.notemaster.domain.repository.NotesRepository

object Injection {

    fun provideRepository(context: Context): NotesRepository {
        val database = NotesDatabase.getInstance(context)

        val localDataSource = LocalDataSource.getInstance(database.NotesDao())

        return NotesRepositoryImpl.getInstance(localDataSource)
    }
}