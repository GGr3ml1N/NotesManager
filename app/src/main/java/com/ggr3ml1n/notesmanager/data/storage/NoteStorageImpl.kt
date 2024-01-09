package com.ggr3ml1n.notesmanager.data.storage

import com.ggr3ml1n.notesmanager.data.database.MainDataBase

class NoteStorageImpl(database: MainDataBase) : NoteStorage {

    val dao = database.getDAO()

    override suspend fun deleteNote() {
        TODO("Not yet implemented")
    }

    override suspend fun saveNote() {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote() {
        TODO("Not yet implemented")
    }

    override suspend fun getNotesByDate() {
        TODO("Not yet implemented")
    }
}