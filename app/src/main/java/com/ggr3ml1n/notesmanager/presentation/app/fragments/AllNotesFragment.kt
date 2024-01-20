package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.notesmanager.databinding.FragmentAllNotesBinding
import com.ggr3ml1n.notesmanager.presentation.app.activities.CurrentNoteActivity
import com.ggr3ml1n.notesmanager.presentation.app.adapters.NoteAdapter
import com.ggr3ml1n.notesmanager.presentation.vm.AllNotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class AllNotesFragment : Fragment() {

    private var _binding: FragmentAllNotesBinding? = null
    private val binding: FragmentAllNotesBinding
        get() = _binding!!

    private lateinit var adapter: NoteAdapter

    private val vm: AllNotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcView()
        //Some kind of collective farm, to be honest
        //start of collective farm
        listObserver()

        binding.datePickerBtn.setOnClickListener {
            datePickerDialog()
            listObserver()
        }
        //end of collective farm

        binding.datePickerBtn.setOnLongClickListener {
            vm.onCalendarButtonLongClick()
            Toast.makeText(activity, "Текущая дата", Toast.LENGTH_SHORT).show()
            true
        }

        binding.newNoteBtn.setOnClickListener {
            startActivity(Intent(requireContext(), CurrentNoteActivity::class.java)
                .apply {
                    putExtra(
                        DATA,
                        vm.date.value?.toLocalDate()
                    )
                }
            )
        }
    }

    private fun datePickerDialog() {
        DatePickerDialog(
            requireContext(),
            listenerDate(),
            vm.date.value!!.year,
            vm.date.value!!.monthValue - 1,
            vm.date.value!!.dayOfMonth
        ).show()
    }

    private fun listenerDate(): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            vm.onCalendarButtonClick(year, month, dayOfMonth)
            listObserver()
            Log.d("DataObserver", "${vm.date.value?.toEpochSecond(ZoneOffset.UTC)}")
        }
    }

    private fun listObserver() {
        vm.date.observe(viewLifecycleOwner) { date ->
            onDateChangeObserver(date)
        }
    }

    private fun onDateChangeObserver(date: LocalDateTime) {
        vm.onDateChanged(date.toEpochSecond(ZoneOffset.UTC)).observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun initRcView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        val listener = object : NoteAdapter.Touchable {
            override fun onClick(note: NoteDomain) {
                startActivity(Intent(requireContext(), CurrentNoteActivity::class.java)
                    .apply {
                        putExtra(NOTE, note)
                        putExtra(DATA, vm.date.value?.toLocalDate())
                    }
                )
            }

            override fun onDelete(note: NoteDomain) {
                vm.onDeleteButtonClick(note)
            }
        }

        adapter = NoteAdapter(listener)
        binding.recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val DATA = "data"
        const val NOTE = "note"

        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}