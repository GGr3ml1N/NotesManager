package com.ggr3ml1n.notesmanager.presentation.di

import com.ggr3ml1n.domain.usecases.DeleteNoteUseCase
import com.ggr3ml1n.domain.usecases.GetNotesByDateUseCase
import com.ggr3ml1n.domain.usecases.GetNotesByNameUseCase
import com.ggr3ml1n.domain.usecases.SaveNoteUseCase
import com.ggr3ml1n.domain.usecases.UpdateNoteUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<DeleteNoteUseCase> {
        DeleteNoteUseCase(noteRepository = get())
    }

    factory<GetNotesByDateUseCase> {
        GetNotesByDateUseCase(noteRepository = get())
    }

    factory<SaveNoteUseCase> {
        SaveNoteUseCase(noteRepository = get())
    }

    factory<UpdateNoteUseCase> {
        UpdateNoteUseCase(noteRepository = get())
    }

    factory<GetNotesByNameUseCase> {
        GetNotesByNameUseCase(noteRepository = get())
    }
}