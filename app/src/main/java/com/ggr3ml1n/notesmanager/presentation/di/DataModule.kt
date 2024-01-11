package com.ggr3ml1n.notesmanager.presentation.di

import com.ggr3ml1n.data.database.MainDataBase
import com.ggr3ml1n.data.repositories.NoteRepositoryImpl
import com.ggr3ml1n.data.storage.NoteStorage
import com.ggr3ml1n.data.storage.NoteStorageImpl
import com.ggr3ml1n.domain.repository.NoteRepository
import org.koin.dsl.module

val dataModule = module {

    single<NoteStorage> {
        NoteStorageImpl(database = get())
    }

    single<MainDataBase> {
        MainDataBase.getDataBase(context = get())
    }

    single<NoteRepository> {
        NoteRepositoryImpl(noteStorage = get())
    }
}