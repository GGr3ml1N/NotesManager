package com.ggr3ml1n.data.storage

import com.ggr3ml1n.data.database.DAO
import com.ggr3ml1n.data.entities.NoteData
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

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

    override fun getNotesByDate(dateStart: String): Flow<List<NoteData>> {
        val calendar = Calendar.getInstance()
        calendar.set(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH],
            23,
            59,
            59)
        return dao.getNotesByDate(
            dateStart = dateStart,
            dateFinish = calendar.time.toString()
        )
    }
}