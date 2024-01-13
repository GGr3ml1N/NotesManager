package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggr3ml1n.notesmanager.databinding.FragmentAllNotesBinding
import com.ggr3ml1n.notesmanager.presentation.app.adapters.NoteAdapter
import com.ggr3ml1n.notesmanager.presentation.vm.AllNotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class AllNotesFragment : Fragment() {

    private var _binding: FragmentAllNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter

    private val vm: AllNotesViewModel by viewModel()

    private lateinit var dateAndTime: Calendar

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

        binding.datePickerBtn.setOnClickListener {
            datePickerDialog()
        }

        binding.datePickerBtn.setOnLongClickListener {
            vm.onLongClick()
            true
        }

        listObserver()
        dateObserver()
        vm.init(dateAndTime.timeInMillis.toString())
    }

    private fun datePickerDialog() {
        DatePickerDialog(
            requireActivity(),
            vm.listener(),
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH],
        ).show()
    }

    private fun initRcView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val listener = NoteAdapter.ClickListener {

        }
        adapter = NoteAdapter(listener)
        binding.recyclerView.adapter = adapter
    }


    private fun listObserver() {
        vm.listOfNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun dateObserver() {
        vm.dateAndTimeLiveData.observe(viewLifecycleOwner) {
            dateAndTime = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}