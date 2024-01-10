package com.ggr3ml1n.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ggr3ml1n.data.entities.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Query("SELECT * FROM Notes WHERE dateStart >= :dateStart AND dateStart < :dateFinish")
    fun getNotesByDate(dateStart: String, dateFinish: String): Flow<List<NoteData>>

    @Query("DELETE FROM Notes WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Insert
    suspend fun saveNote(note: NoteData)

    @Update
    suspend fun updateNote(note: NoteData)
}