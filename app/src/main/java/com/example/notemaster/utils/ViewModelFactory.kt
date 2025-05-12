package com.example.notemaster.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notemaster.di.Injection
import com.example.notemaster.domain.repository.NotesRepository
import com.example.notemaster.view.presentation.home.HomeViewModel
import com.example.notemaster.view.presentation.notes.NotesViewModel

class ViewModelFactory private constructor(private val notesRepository: NotesRepository) :
ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(notesRepository) as T
            }
            modelClass.isAssignableFrom(NotesViewModel::class.java) -> {
                NotesViewModel(notesRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class : " + modelClass.name)
        }

}