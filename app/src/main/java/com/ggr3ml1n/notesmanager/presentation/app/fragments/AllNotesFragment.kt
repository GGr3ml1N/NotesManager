package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggr3ml1n.notesmanager.databinding.FragmentAllNotesBinding
import com.ggr3ml1n.notesmanager.presentation.app.activities.CurrentNoteActivity
import com.ggr3ml1n.notesmanager.presentation.app.adapters.NoteAdapter
import com.ggr3ml1n.notesmanager.presentation.vm.AllNotesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRcView()
        //Some kind of collective farm, to be honest
        //start of kolhoz
        vm.listObserver(adapter, viewLifecycleOwner)

        binding.datePickerBtn.setOnClickListener {
            vm.datePickerDialog(requireActivity(), adapter, viewLifecycleOwner)
            vm.listObserver(adapter, viewLifecycleOwner)
        }
        //end of kolhoz

        binding.datePickerBtn.setOnLongClickListener {
            vm.onLongClick()
            Toast.makeText(activity, "Текущая дата", Toast.LENGTH_SHORT).show()
            true
        }

        binding.newNoteBtn.setOnClickListener {
            startActivity(Intent(requireContext(), CurrentNoteActivity::class.java)
                .apply {
                    putExtra(
                        DATA,
                        vm.date.value
                    )
                }
            )
        }
    }


    private fun initRcView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val listener = NoteAdapter.ClickListener {

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

        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}