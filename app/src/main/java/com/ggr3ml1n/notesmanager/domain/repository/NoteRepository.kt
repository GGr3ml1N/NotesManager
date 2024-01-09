package com.ggr3ml1n.notesmanager.domain.repository

interface NoteRepository {

    suspend fun deleteNote()

    suspend fun saveNote()

    suspend fun updateNote()

    suspend fun getNotesByDate()
}