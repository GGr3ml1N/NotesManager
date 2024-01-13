package com.ggr3ml1n.notesmanager.presentation.vm

import android.app.DatePickerDialog
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

    private val _dateAndTimeLiveData = MutableLiveData(Calendar.getInstance())
    val dateAndTimeLiveData: LiveData<Calendar> = _dateAndTimeLiveData

    private val _listOfNotes: MutableLiveData<List<NoteDomain>> = MutableLiveData()
    val listOfNotes: LiveData<List<NoteDomain>> = _listOfNotes

    fun init(dayStart: String) {
        viewModelScope.launch {
            _listOfNotes.value = getNotesByDateUseCase.execute(dayStart).asLiveData().value
        }
    }

     fun listener(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            _dateAndTimeLiveData.value?.set(year, month, dayOfMonth)
            init(_dateAndTimeLiveData.value?.timeInMillis.toString())
        }
    }

    fun onLongClick() {
        _dateAndTimeLiveData.value = Calendar.getInstance()
    }

   /* class AllNotesProvider(context: Context) : ViewModelProvider.Factory {

        private val getNotesByDateUseCase: GetNotesByDateUseCase by lazy {
            GetNotesByDateUseCase(
                NoteRepositoryImpl(
                    NoteStorageImpl(
                        (context.applicationContext as MainApp).dataBase.getDAO()
                    )
                )
            )
        }

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AllNotesViewModel(getNotesByDateUseCase) as T
        }
    }*/
}