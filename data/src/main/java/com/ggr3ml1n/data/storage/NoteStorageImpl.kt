package com.ggr3ml1n.data.storage

import com.ggr3ml1n.data.database.DAO
import com.ggr3ml1n.data.entities.NoteData
import kotlinx.coroutines.flow.Flow

class NoteStorageImpl(private val dao: DAO) : NoteStorage {


    override suspend fun deleteNote(id: Int) {
        dao.deleteNote(id = id)
    }

    override suspend fun saveNote(note: NoteData) {
        dao.saveNote(note = note)
    }

    override suspend fun updateNote(note: NoteData) {
        dao.updateNote(note = note)
    }

    override fun getNotesByDate(dateStart: Long): Flow<List<NoteData>> {
        return dao.getNotesByDate(
            dateStart = dateStart,
            dateFinish = dateStart + 86400000L
        )
    }

    override fun getNoteByName(name: String): Flow<List<NoteData>> {
        return dao.getNoteByName(name)
    }
}