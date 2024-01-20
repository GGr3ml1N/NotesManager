package com.ggr3ml1n.notesmanager.presentation.vm

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.domain.usecases.DeleteNoteUseCase
import com.ggr3ml1n.domain.usecases.GetNotesByDateUseCase
import com.ggr3ml1n.notesmanager.presentation.app.adapters.NoteAdapter
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

class AllNotesViewModel(
    private val getNotesByDateUseCase: GetNotesByDateUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _date = MutableLiveData(Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    })

    //I want to redo getting the current date
    //private val __date = MutableLiveData(Instant.now().atZone(ZoneId.of("UTC")).toLocalDate())

    val date: LiveData<Calendar> = _date
    private fun getListByDate(time: Long) =
        getNotesByDateUseCase.execute(time).asLiveData()

    fun datePickerDialog(context: Context, adapter: NoteAdapter, lifecycleOwner: LifecycleOwner) {
        DatePickerDialog(
            context,
            listenerDate(adapter, lifecycleOwner),
            _date.value!![Calendar.YEAR],
            _date.value!![Calendar.MONTH],
            _date.value!![Calendar.DAY_OF_MONTH],
        ).show()
    }

    fun delete(noteDomain: NoteDomain) = viewModelScope.launch {
        deleteNoteUseCase.execute(noteDomain)
    }

    private fun listenerDate(adapter: NoteAdapter, lifecycleOwner: LifecycleOwner): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            _date.value!!.apply {
                set(year, month, dayOfMonth)
            }
            listObserver(adapter, lifecycleOwner)
            Log.d("DataObserver", "${_date.value?.timeInMillis}")
        }
    }

    fun listObserver(adapter: NoteAdapter, lifecycleOwner: LifecycleOwner) {
        date.observe(lifecycleOwner) { date ->
            Log.d("Data", "${date.timeInMillis}")
            getListByDate(date.timeInMillis).observe(lifecycleOwner) { list ->
                adapter.submitList(list)
                Log.d("List", "$list")
            }
        }
    }

    fun onLongClick() {
        _date.value = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}