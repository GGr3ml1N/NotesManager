package com.ggr3ml1n.notesmanager.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.SaveNoteUseCase
import com.ggr3ml1n.domain.usecases.UpdateNoteUseCase
import com.ggr3ml1n.notesmanager.presentation.app.activities.CurrentNoteActivity
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.time.LocalDate

class CurrentNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {

    private val _note = MutableLiveData<NoteDomain>()
    val note: LiveData<NoteDomain> = _note

    private val _date = MutableLiveData<LocalDate>()
    val date: LiveData<LocalDate> = _date

    fun onDateStartChanged(dateStart: Timestamp) {
        _note.value = _note.value?.copy(dateStart = dateStart)
    }

    fun onDateFinishChanged(dateFinish: Timestamp) {
        _note.value = _note.value?.copy(dateFinish = dateFinish)
    }

    fun onNameChanged(name: String){
        _note.value = _note.value?.copy(name = name)
    }
    fun onDescriptionChanged(description: String){
        _note.value = _note.value?.copy(description = description)
    }

    fun onSaveButtonClick(note: NoteDomain, action: CurrentNoteActivity.Action) =
        viewModelScope.launch {
            if (action == CurrentNoteActivity.Action.SAVE)
                saveNoteUseCase.execute(note)
            if (action == CurrentNoteActivity.Action.UPDATE)
                updateNoteUseCase.execute(note)
        }

    fun onTvDateClick(year: Int, monthOfYear: Int, dayOfMonth: Int){
        _date.value = LocalDate.of(year, monthOfYear+1, dayOfMonth)
    }

    fun onGettingNote(note: NoteDomain) {
        _note.value = note
    }

    fun onGettingDate(date: LocalDate) {
        _date.value = date
    }
}