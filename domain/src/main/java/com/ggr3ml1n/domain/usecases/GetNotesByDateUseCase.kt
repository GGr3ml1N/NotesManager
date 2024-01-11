package com.ggr3ml1n.domain.usecases

import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesByDateUseCase(private val noteRepository: NoteRepository) {

    fun execute(note: NoteDomain): Flow<List<NoteDomain>> {
        return noteRepository.getNotesByDate(
            dateStart = note.dateStart.toString(),
            dateFinish = note.dateFinish.toString(),
        )
    }
}