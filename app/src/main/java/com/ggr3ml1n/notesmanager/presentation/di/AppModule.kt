package com.ggr3ml1n.notesmanager.presentation.di

import com.ggr3ml1n.notesmanager.presentation.vm.AllNotesViewModel
import com.ggr3ml1n.notesmanager.presentation.vm.CurrentNoteViewModel
import com.ggr3ml1n.notesmanager.presentation.vm.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module{

    viewModel<AllNotesViewModel>{
        AllNotesViewModel(
            getNotesByDateUseCase = get(),
            deleteNoteUseCase = get(),
            getNotesByNameUseCase = get()
        )
    }
    viewModel<CurrentNoteViewModel>{
        CurrentNoteViewModel(
            saveNoteUseCase = get(),
            updateNoteUseCase = get()
        )
    }
    viewModel<MainViewModel>{
        MainViewModel()
    }
}