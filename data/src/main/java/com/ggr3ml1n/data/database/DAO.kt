package com.ggr3ml1n.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ggr3ml1n.data.entities.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Query("SELECT * FROM notes WHERE dateStart >= :dateStart AND dateFinish < :dateFinish ORDER BY dateStart, dateFinish, name")
    fun getNotesByDate(dateStart: Long, dateFinish: Long): Flow<List<NoteData>>

    @Query("DELETE FROM Notes WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Insert
    suspend fun saveNote(note: NoteData)

    @Update
    suspend fun updateNote(note: NoteData)

    @Query("SELECT * FROM notes WHERE name LIKE :name ORDER BY name, dateStart")
    fun getNoteByName(name: String): Flow<List<NoteData>>
}