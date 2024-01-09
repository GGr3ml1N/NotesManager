package com.ggr3ml1n.notesmanager.domain.usecases

import com.ggr3ml1n.notesmanager.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend fun execute() {
        noteRepository.deleteNote()
    }
}