package com.ggr3ml1n.notesmanager.presentation.di

import androidx.room.Room
import com.ggr3ml1n.data.database.MainDataBase
import com.ggr3ml1n.data.repositories.NoteRepositoryImpl
import com.ggr3ml1n.data.storage.NoteStorage
import com.ggr3ml1n.data.storage.NoteStorageImpl
import com.ggr3ml1n.domain.repository.NoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single<NoteStorage> {
        NoteStorageImpl(get())
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            MainDataBase::class.java,
            "NotesManager.db"
        ).build()
    }

    single {
        val dataBase = get<MainDataBase>()
        dataBase.getDAO()
    }

    single<NoteRepository> {
        NoteRepositoryImpl(noteStorage = get())
    }
}