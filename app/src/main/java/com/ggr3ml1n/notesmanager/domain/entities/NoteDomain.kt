package com.ggr3ml1n.notesmanager.domain.entities

import java.sql.Timestamp

data class NoteDomain(
    val id: Int,
    val dateStart: Timestamp,
    val dateFinish: Timestamp,
    val name: String,
    val description: String,
)