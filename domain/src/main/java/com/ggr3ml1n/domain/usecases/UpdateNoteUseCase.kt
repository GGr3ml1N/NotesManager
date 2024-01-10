package com.ggr3ml1n.domain.usecases

import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.repository.NoteRepository

class UpdateNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(note: NoteDomain) {
        noteRepository.updateNote(note = note)
    }
}