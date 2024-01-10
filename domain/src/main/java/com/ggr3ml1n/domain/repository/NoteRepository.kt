package com.ggr3ml1n.domain.repository

import com.ggr3ml1n.domain.entities.NoteDomain

interface NoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: NoteDomain)

    suspend fun updateNote(note: NoteDomain)

    fun getNotesByDate(dateStart: String, dateFinish: String): List<NoteDomain>
}