package com.ggr3ml1n.notesmanager.presentation.vm

import androidx.lifecycle.ViewModel
import com.ggr3ml1n.domain.usecases.DeleteNoteUseCase
import com.ggr3ml1n.domain.usecases.SaveNoteUseCase
import com.ggr3ml1n.domain.usecases.UpdateNoteUseCase

class CurrentNoteViewModel(
    saveNoteUseCase: SaveNoteUseCase,
    deleteNoteUseCase: DeleteNoteUseCase,
    updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {
}