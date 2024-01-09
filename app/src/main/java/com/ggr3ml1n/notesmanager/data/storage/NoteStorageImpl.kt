package com.ggr3ml1n.notesmanager.data.storage

import androidx.lifecycle.asLiveData
import com.ggr3ml1n.notesmanager.data.database.MainDataBase
import com.ggr3ml1n.notesmanager.data.entities.NoteData

class NoteStorageImpl(database: MainDataBase) : NoteStorage {

    val dao = database.getDAO()

    override suspend fun deleteNote(id: Int) {
        dao.deleteNote(id = id)
    }

    override suspend fun saveNote(note: NoteData) {
        dao.saveNote(note = note)
    }

    override suspend fun updateNote(note: NoteData) {
        dao.updateNote(note = note)
    }

    override fun getNotesByDate(dateStart: String, dateFinish: String) =
        dao.getNotesByDate(
            dateStart = dateStart,
            dateFinish = dateFinish
        ).asLiveData()
}