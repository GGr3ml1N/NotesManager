package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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

    private val launch: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                TODO()
            }
        }


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
            vm.datePickerDialog(requireActivity())
        }

        binding.datePickerBtn.setOnLongClickListener {
            vm.onLongClick()
            true
        }

        binding.newNoteBtn.setOnClickListener {
            launch.launch(Intent(activity, CurrentNoteActivity::class.java))
        }

        listObserver()
        vm.init()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}