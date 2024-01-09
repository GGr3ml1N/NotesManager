package com.ggr3ml1n.notesmanager.domain.usecases

import androidx.lifecycle.LiveData
import com.ggr3ml1n.notesmanager.domain.entities.NoteDomain
import com.ggr3ml1n.notesmanager.domain.repository.NoteRepository

class GetNotesByPageUseCase(private val noteRepository: NoteRepository) {

    fun execute(note: NoteDomain) : LiveData<List<NoteDomain>> {
        return noteRepository.getNotesByDate(
            dateStart = note.dateStart.toString(),
            dateFinish = note.dateFinish.toString(),
        )
    }
}