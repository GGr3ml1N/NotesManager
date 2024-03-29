package com.ggr3ml1n.data.storage

import com.ggr3ml1n.data.entities.NoteData
import kotlinx.coroutines.flow.Flow

interface NoteStorage {

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: NoteData)

    suspend fun updateNote(note: NoteData)

    fun getNotesByDate(dateStart: Long): Flow<List<NoteData>>

    fun getNoteByName(name: String): Flow<List<NoteData>>
}