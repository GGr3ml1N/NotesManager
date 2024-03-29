package com.ggr3ml1n.domain.repository

import com.ggr3ml1n.domain.entities.NoteDomain
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun saveNote(note: NoteDomain)

    suspend fun updateNote(note: NoteDomain)

    fun getNotesByDate(dateStart: Long): Flow<List<NoteDomain>>

    fun getNoteByName(name: String): Flow<List<NoteDomain>>
}