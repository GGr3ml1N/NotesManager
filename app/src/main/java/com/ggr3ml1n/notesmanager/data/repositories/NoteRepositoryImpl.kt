package com.ggr3ml1n.notesmanager.data.repositories

import com.ggr3ml1n.notesmanager.data.storage.NoteStorage
import com.ggr3ml1n.notesmanager.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteStorage: NoteStorage) : NoteRepository {

    override suspend fun deleteNote() {
        noteStorage.deleteNote()
    }

    override suspend fun saveNote() {
        noteStorage.saveNote()
    }

    override suspend fun updateNote() {
        noteStorage.updateNote()
    }

    override suspend fun getNotesByDate() {
        noteStorage.getNotesByDate()
    }
}