package com.ggr3ml1n.notesmanager.data.storage

interface NoteStorage {

    suspend fun deleteNote()

    suspend fun saveNote()

    suspend fun updateNote()

    suspend fun getNotesByDate()
}