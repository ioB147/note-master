package com.example.notemaster.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notemaster.data.local.entity.NotesEntity

@Database(entities = [NotesEntity::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
}