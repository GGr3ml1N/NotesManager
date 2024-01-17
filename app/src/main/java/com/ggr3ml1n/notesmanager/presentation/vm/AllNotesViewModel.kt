package com.ggr3ml1n.notesmanager.presentation.vm

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.GetNotesByDateUseCase
import java.util.Calendar

class AllNotesViewModel(
    getNotesByDateUseCase: GetNotesByDateUseCase
) : ViewModel() {

    var date: Calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
        private set

    val listOfNotes: LiveData<List<NoteDomain>> = getNotesByDateUseCase.execute(date.timeInMillis.toString()).asLiveData()

    fun datePickerDialog(context: Context) {
        DatePickerDialog(
            context,
            listenerDate(),
            date[Calendar.YEAR],
            date[Calendar.MONTH],
            date[Calendar.DAY_OF_MONTH],
        ).show()
    }

    private fun listenerDate(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            date.set(year, month, dayOfMonth)
        }
    }

    fun onLongClick() {
        date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}