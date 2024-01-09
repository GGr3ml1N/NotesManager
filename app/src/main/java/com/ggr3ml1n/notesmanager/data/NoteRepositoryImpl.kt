package com.ggr3ml1n.notesmanager.data

import com.ggr3ml1n.notesmanager.domain.NoteRepository

class NoteRepositoryImpl(dataBase: MainDataBase) : NoteRepository {
    val dao = dataBase.getDAO()
}