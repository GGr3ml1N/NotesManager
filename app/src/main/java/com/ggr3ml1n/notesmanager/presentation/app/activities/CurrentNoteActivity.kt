package com.ggr3ml1n.notesmanager.presentation.app.activities

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.notesmanager.R
import com.ggr3ml1n.notesmanager.databinding.ActivityCurrentNoteBinding
import com.ggr3ml1n.notesmanager.presentation.app.fragments.AllNotesFragment
import com.ggr3ml1n.notesmanager.presentation.app.utils.getSerializable
import com.ggr3ml1n.notesmanager.presentation.vm.CurrentNoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CurrentNoteActivity : AppCompatActivity() {

    private var _binding: ActivityCurrentNoteBinding? = null
    private val binding get() = _binding!!

    private val vm: CurrentNoteViewModel by viewModel()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrentNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        menuButtonListener()
        onStartDateClick()
        onFinishDateClick()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun onStartDateClick() = with(binding) {
        tvTimeStart.setOnClickListener {
            TimePickerDialog(
                this@CurrentNoteActivity, { _, hourOfDay, minute ->

                    tvTimeStart.text = LocalTime.of(hourOfDay, minute).toString()

                    val compare = LocalTime.parse(tvTimeStart.text).compareTo(LocalTime.parse(tvTimeEnd.text))
                    if (compare == 1 || compare == 0 )
                        tvTimeEnd.text = LocalTime.parse(tvTimeStart.text).plusMinutes(1L).toString()
                },
                0, 0, true
            ).show()
        }
    }

    private fun onFinishDateClick() = with(binding) {
        tvTimeEnd.setOnClickListener {
            TimePickerDialog(
                this@CurrentNoteActivity,
                { _, hourOfDay, minute ->

                    val timeEnd = LocalTime.of(hourOfDay, minute)
                    val compare = LocalTime.parse(tvTimeStart.text).compareTo(timeEnd)

                    if (compare == -1) {
                        tvTimeEnd.text = timeEnd.toString()
                    }
                    else {
                        tvTimeEnd.text =
                            LocalTime.parse(tvTimeStart.text).plusMinutes(1L).toString()
                    }
                },
                0,
                0,
                true
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun menuButtonListener() = with(binding) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            /*There should be a Dialog here that
            will clarify whether the user really
            wants to exit without saving*/
            finish()
        }
        toolbar.title =
            SimpleDateFormat("dd M", Locale.getDefault()).format(Date(getData(intent)!!.timeInMillis))
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    saveNote()
                }
            }
            true
        }
    }

    private fun saveNote() = with(binding) {
        val timeFormat = SimpleDateFormat("HH:mm").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        vm.save(
            dateStart = Timestamp(
                timeFormat.parse(tvTimeStart.text.toString())!!.time + getData(intent)!!.timeInMillis
            ),
            dateFinish = Timestamp(
                timeFormat.parse(tvTimeEnd.text.toString())!!.time + getData(intent)!!.timeInMillis
            ),
            name = edName.text.toString(),
            description = edDescription.text.toString()
        )
        finish()
    }
    
    private fun getData(intent: Intent): Calendar? {
        return intent.getSerializable(
            AllNotesFragment.DATA,
            Calendar::class.java
        )
    }
}