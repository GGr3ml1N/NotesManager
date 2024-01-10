package com.ggr3ml1n.data.storage

import androidx.lifecycle.LiveData
import com.ggr3ml1n.data.entities.NoteData

interface NoteStorage {

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: NoteData)

    suspend fun updateNote(note: NoteData)

    fun getNotesByDate(dateStart: String, dateFinish: String): LiveData<List<NoteData>>
}