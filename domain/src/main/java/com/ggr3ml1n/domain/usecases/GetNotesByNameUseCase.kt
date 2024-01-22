package com.ggr3ml1n.domain.usecases

import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesByNameUseCase(private val noteRepository: NoteRepository) {

    fun execute(name:String) : Flow<List<NoteDomain>> {
        return noteRepository.getNoteByName(name)
    }
}