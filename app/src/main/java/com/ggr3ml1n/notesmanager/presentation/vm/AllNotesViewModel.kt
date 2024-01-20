package com.ggr3ml1n.notesmanager.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.DeleteNoteUseCase
import com.ggr3ml1n.domain.usecases.GetNotesByDateUseCase
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AllNotesViewModel(
    private val getNotesByDateUseCase: GetNotesByDateUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _date = MutableLiveData(
        LocalDateTime.of(
            Instant.now().atZone(ZoneId.of("UTC")).toLocalDate(),
            LocalTime.of(0, 0, 0, 0)
        )
    )

    val date: LiveData<LocalDateTime> = _date

    fun onDateChanged(time: Long) =
        getNotesByDateUseCase.execute(time*1000).asLiveData()


    fun onDeleteButtonClick(noteDomain: NoteDomain) = viewModelScope.launch {
        deleteNoteUseCase.execute(noteDomain)
    }

    fun onCalendarButtonLongClick() {
        _date.value = LocalDateTime.of(
            Instant.now().atZone(ZoneId.of("UTC")).toLocalDate(),
            LocalTime.of(0, 0, 0, 0)
        )
        Log.d("DateNow", _date.value.toString())
    }

    fun onCalendarButtonClick(year: Int, monthOfYear: Int, dayOfMonth: Int){
        _date.value = LocalDateTime.of(LocalDate.of(year, monthOfYear+1, dayOfMonth), LocalTime.of(0,0,0,0))
        Log.d("DateNow", _date.value.toString())
    }
}