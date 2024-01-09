package com.ggr3ml1n.notesmanager.domain.repository

import androidx.lifecycle.LiveData
import com.ggr3ml1n.notesmanager.domain.entities.NoteDomain

interface NoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: NoteDomain)

    suspend fun updateNote(note: NoteDomain)

    fun getNotesByDate(dateStart: String, dateFinish: String) : LiveData<List<NoteDomain>>
}