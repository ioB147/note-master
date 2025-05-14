package com.example.notemaster.di

import android.content.Context
import androidx.room.Room
import com.example.notemaster.data.local.LocalDataSource
import com.example.notemaster.data.local.room.NotesDao
import com.example.notemaster.data.local.room.NotesDatabase
import com.example.notemaster.data.remote.FirebaseDataSource
import com.example.notemaster.data.repository.NotesRepositoryImpl
import com.example.notemaster.domain.repository.NotesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDataSource(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirebaseDataSource {
        return FirebaseDataSource(firestore, auth)
    }

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(database: NotesDatabase): NotesDao {
        return database.NotesDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(notesDao: NotesDao): LocalDataSource {
        return LocalDataSource(notesDao)
    }

    @Provides
    @Singleton
    fun provideNotesRepository(
        localDataSource: LocalDataSource,
        firebaseDataSource: FirebaseDataSource
    ): NotesRepository {
        return NotesRepositoryImpl(localDataSource, firebaseDataSource)
    }
}