package com.ggr3ml1n.data.repositories

import com.ggr3ml1n.data.entities.NoteData
import com.ggr3ml1n.data.storage.NoteStorage
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(private val noteStorage: NoteStorage) : NoteRepository {

    override suspend fun deleteNote(id: Int) {
        noteStorage.deleteNote(id = id)
    }

    override suspend fun saveNote(note: NoteDomain) {
        noteStorage.saveNote(toData(note))
    }

    override suspend fun updateNote(note: NoteDomain) {
        noteStorage.saveNote(toData(note))
    }

    override fun getNotesByDate(dateStart: String, dateFinish: String): Flow<List<NoteDomain>> =
        noteStorage.getNotesByDate(dateStart = dateStart, dateFinish = dateFinish)
            .map { list ->
                list.map { noteData ->
                    toDomain(noteData)
                }
            }

    private fun toDomain(note: NoteData): NoteDomain {
        return NoteDomain(
            id = note.id,
            dateStart = note.dateStart,
            dateFinish = note.dateFinish,
            name = note.name,
            description = note.description
        )
    }

    private fun toData(note: NoteDomain): NoteData {
        return NoteData(
            id = note.id,
            dateStart = note.dateStart,
            dateFinish = note.dateFinish,
            name = note.name,
            description = note.description
        )
    }
}