package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ggr3ml1n.notesmanager.databinding.FragmentAllNotesBinding
import java.util.Calendar
import java.util.GregorianCalendar

class AllNotesFragment : Fragment() {

    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!

    private val dateAndTime = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.datePickerBtn.setOnClickListener {
            setDate()
        }
        binding.datePickerBtn.setOnLongClickListener {
            setTime()
            true
        }
    }

    private fun setDate() {
        DatePickerDialog(
            requireActivity(),
            { _, year, month, dayOfMonth ->
                Log.d(
                    "datap",
                    "checked date: ${
                        GregorianCalendar(
                            year,
                            month,
                            dayOfMonth
                        ).timeInMillis
                    }\n current date: ${Calendar.getInstance().timeInMillis}"
                )
            },
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH],
        ).show()
    }

    private fun setTime() {
        TimePickerDialog(requireActivity(),
            { _, hourOfDay, minute ->
                Log.d(
                    "datat", "picked: ${
                        GregorianCalendar(
                            dateAndTime[Calendar.YEAR],
                            dateAndTime[Calendar.MONTH],
                            dateAndTime[Calendar.DAY_OF_MONTH],
                            hourOfDay,
                            minute
                        ).timeInMillis
                    }\n current: current date: ${Calendar.getInstance().timeInMillis}"
                )
            }, 0, 0, true).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}