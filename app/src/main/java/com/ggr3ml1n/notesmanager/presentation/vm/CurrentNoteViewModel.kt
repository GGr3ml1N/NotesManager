package com.ggr3ml1n.notesmanager.presentation.vm

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.DeleteNoteUseCase
import com.ggr3ml1n.domain.usecases.SaveNoteUseCase
import com.ggr3ml1n.domain.usecases.UpdateNoteUseCase
import kotlinx.coroutines.launch
import java.sql.Timestamp

class CurrentNoteViewModel(
    private val saveNoteUseCase: SaveNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
) : ViewModel() {
    fun timePicker(context: Context, textView: TextView) {
        TimePickerDialog(context, timeListener(textView), 0, 0, true).show()
    }

    private fun timeListener(textView: TextView) =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val hours: String = if (hourOfDay in 0..9) "0$hourOfDay" else hourOfDay.toString()
            val minutes: String = if (minute in 0..9) "0$minute" else minute.toString()
            textView.text = "$hours:$minutes"
        }

    private fun saveNote(note: NoteDomain) = viewModelScope.launch {
        saveNoteUseCase.execute(note)
    }

    fun save(dateStart: Timestamp, dateFinish: Timestamp, name: String, description: String) {
        val note = NoteDomain(
            id = null,
            dateStart = dateStart,
            dateFinish = dateFinish,
            name = name,
            description = description
        )
        saveNote(note)
    }
}