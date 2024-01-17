package com.ggr3ml1n.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "dateStart") val dateStart: Long,
    @ColumnInfo(name = "dateFinish") val dateFinish: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
)