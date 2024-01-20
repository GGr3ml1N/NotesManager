package com.ggr3ml1n.notesmanager.presentation.app.activities

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.notesmanager.R
import com.ggr3ml1n.notesmanager.databinding.ActivityCurrentNoteBinding
import com.ggr3ml1n.notesmanager.presentation.app.fragments.AllNotesFragment
import com.ggr3ml1n.notesmanager.presentation.app.utils.getSerializable
import com.ggr3ml1n.notesmanager.presentation.vm.CurrentNoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

class CurrentNoteActivity : AppCompatActivity() {

    private var _binding: ActivityCurrentNoteBinding? = null
    private val binding: ActivityCurrentNoteBinding
        get() = _binding!!

    private val vm: CurrentNoteViewModel by viewModel()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCurrentNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fillNote()
        menuButtonListener()
        onStartDateClick()
        onFinishDateClick()
    }

    private fun onStartDateClick() = with(binding) {
        tvTimeStart.setOnClickListener {
            TimePickerDialog(
                this@CurrentNoteActivity, { _, hourOfDay, minute ->

                    tvTimeStart.text = LocalTime.of(hourOfDay, minute).toString()

                    val compare =
                        LocalTime.parse(tvTimeStart.text).compareTo(LocalTime.parse(tvTimeEnd.text))
                    if (compare == 1 || compare == 0)
                        tvTimeEnd.text =
                            LocalTime.parse(tvTimeStart.text).plusMinutes(1L).toString()
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
                    } else {
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

    private fun menuButtonListener() = with(binding) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener {
            /*There should be a Dialog here that
            will clarify whether the user really
            wants to exit without saving*/
            finish()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    onSaveButtonClick()
                }
            }
            true
        }
    }

    private fun fillNote() = with(binding) {
        if (intent.hasExtra(AllNotesFragment.NOTE)) {
            vm.onGettingNote(
                getNote() ?: NoteDomain(null, Timestamp(0L), Timestamp(1L), "", "")
            )

            edName.setText(vm.note.value?.name)

            tvTimeStart.text =
                vm.note.value?.dateStart?.toInstant()?.atZone(ZoneId.of("UTC"))?.toLocalDateTime()
                    ?.toLocalTime().toString()

            tvTimeEnd.text =
                vm.note.value?.dateFinish?.toInstant()?.atZone(ZoneId.of("UTC"))?.toLocalDateTime()
                    ?.toLocalTime().toString()

            edDescription.setText(vm.note.value?.description)
        }
    }

    private fun onSaveButtonClick() = with(binding) {
        if (intent.hasExtra(AllNotesFragment.NOTE)) {
            vm.onDateStartChanged(
                Timestamp(
                    LocalDateTime.of(getDate(), LocalTime.parse(tvTimeStart.text)).toInstant(
                        ZoneOffset.UTC
                    ).toEpochMilli()
                )
            )

            vm.onDateFinishChanged(
                Timestamp(
                    LocalDateTime.of(getDate(), LocalTime.parse(tvTimeEnd.text)).toInstant(
                        ZoneOffset.UTC
                    ).toEpochMilli()
                )
            )

            vm.onNameChanged(edName.text.toString().trim())

            vm.onDescriptionChanged(edDescription.text.toString().trim())

            vm.onSaveButtonClick(vm.note.value!!, Action.UPDATE)
        } else {
            val note = NoteDomain(
                id = null,
                dateStart = Timestamp(
                    LocalDateTime.of(getDate(), LocalTime.parse(tvTimeStart.text)).toInstant(
                        ZoneOffset.UTC
                    ).toEpochMilli()
                ),
                dateFinish = Timestamp(
                    LocalDateTime.of(getDate(), LocalTime.parse(tvTimeEnd.text)).toInstant(
                        ZoneOffset.UTC
                    ).toEpochMilli()
                ),
                name = edName.text.toString().trim(),
                description = edDescription.text.toString().trim()
            )

            vm.onSaveButtonClick(note, Action.SAVE)
        }
        finish()
    }

    private fun getDate(): LocalDate? {
        return intent.getSerializable(
            AllNotesFragment.DATA,
            LocalDate::class.java
        )
    }

    private fun getNote(): NoteDomain? {
        return intent.getSerializable(
            AllNotesFragment.NOTE,
            NoteDomain::class.java
        )
    }

    enum class Action {
        SAVE, UPDATE
    }
}