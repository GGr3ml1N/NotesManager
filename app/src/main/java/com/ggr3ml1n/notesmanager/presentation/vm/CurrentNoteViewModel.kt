package com.ggr3ml1n.notesmanager.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.SaveNoteUseCase
import com.ggr3ml1n.domain.usecases.UpdateNoteUseCase
import kotlinx.coroutines.launch

class CurrentNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {

    fun saveNote(note: NoteDomain) = viewModelScope.launch {
        saveNoteUseCase.execute(note)
    }

    fun updateNote(note: NoteDomain) = viewModelScope.launch {
        updateNoteUseCase.execute(note)
    }
}