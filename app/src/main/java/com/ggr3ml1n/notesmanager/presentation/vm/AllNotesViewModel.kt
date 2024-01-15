package com.ggr3ml1n.notesmanager.presentation.vm

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.GetNotesByDateUseCase
import kotlinx.coroutines.launch
import java.util.Calendar

class AllNotesViewModel(
    private val getNotesByDateUseCase: GetNotesByDateUseCase
) : ViewModel() {

    private val _listOfNotes: MutableLiveData<List<NoteDomain>> = MutableLiveData()
    val listOfNotes: LiveData<List<NoteDomain>> = _listOfNotes

    private var dateAndTime: Calendar = Calendar.getInstance()

    fun init() {
        viewModelScope.launch {
            _listOfNotes.value = getNotesByDateUseCase
                .execute(dateAndTime.timeInMillis.toString())
                .asLiveData().value
        }
    }

    fun datePickerDialog(context: Context) {
        DatePickerDialog(
            context,
            listener(),
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH],
        ).show()
    }

    private fun listener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            dateAndTime.set(year, month, dayOfMonth)
            init()
        }
    }

    fun onLongClick(){
        dateAndTime = Calendar.getInstance()
    }
}