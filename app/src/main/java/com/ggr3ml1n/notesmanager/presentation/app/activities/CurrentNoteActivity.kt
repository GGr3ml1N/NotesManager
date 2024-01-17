package com.ggr3ml1n.notesmanager.presentation.app.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.notesmanager.R
import com.ggr3ml1n.notesmanager.databinding.ActivityCurrentNoteBinding
import com.ggr3ml1n.notesmanager.presentation.app.fragments.AllNotesFragment
import com.ggr3ml1n.notesmanager.presentation.vm.CurrentNoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
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

    private fun onStartDateClick() = with(binding) {
        tvTimeStart.setOnClickListener {
            vm.timePicker(this@CurrentNoteActivity, tvTimeStart)
        }
    }

    private fun onFinishDateClick() = with(binding) {
        tvTimeEnd.setOnClickListener {
            vm.timePicker(this@CurrentNoteActivity, tvTimeEnd)
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
            SimpleDateFormat("dd M", Locale.getDefault()).format(Date(getData(intent).timeInMillis))
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    saveNote()
                }

                R.id.delete -> {}
            }
            true
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun saveNote() = with(binding) {
        val timeFormat = SimpleDateFormat("HH:mm").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        vm.save(
            dateStart = Timestamp(
                timeFormat.parse(tvTimeStart.text.toString())!!.time + getData(intent).timeInMillis
            ),
            dateFinish = Timestamp(
                    timeFormat.parse(tvTimeEnd.text.toString())!!.time + getData(intent).timeInMillis
            ),
            name = edName.text.toString(),
            description = edDescription.text.toString()
        )
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getData(intent: Intent): Calendar {
        return intent.getSerializableExtra(
            AllNotesFragment.DATA,
            Calendar::class.java
        ) ?: throw Exception("Null \"CheckedData\" instance")
    }
}