package com.ggr3ml1n.domain.usecases

import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute(note: NoteDomain) {
        noteRepository.deleteNote(id = note.id)
    }
}