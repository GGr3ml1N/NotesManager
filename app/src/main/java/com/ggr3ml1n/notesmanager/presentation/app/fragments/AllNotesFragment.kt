package com.ggr3ml1n.notesmanager.presentation.app.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.NestedScrollView
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
import java.time.format.DateTimeFormatter

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

        onDateButtonClick()
        //end of collective farm

        onDateButtonLongClick()

        onNewNoteButtonClick()

        onSearchViewClick()

        onCloseButtonClick()

        onScrollView()
    }


    private fun onNewNoteButtonClick() {
        binding.newNoteBtn.setOnClickListener {
            startActivity(Intent(requireContext(), CurrentNoteActivity::class.java)
                .apply { putExtra(DATA, vm.date.value?.toLocalDate()) }
            )
        }
    }

    private fun onDateButtonLongClick() {
        binding.datePickerBtn.setOnLongClickListener {
            vm.onCalendarButtonLongClick()
            Toast.makeText(
                activity,
                "Текущая дата: ${
                    DateTimeFormatter.ofPattern("dd MMMM yyyy").format(vm.date.value)
                }",
                Toast.LENGTH_LONG
            ).show()
            true
        }
    }

    private fun onDateButtonClick() {
        binding.datePickerBtn.setOnClickListener {
            datePickerDialog()
            listObserver()
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
        }
    }

    private fun listObserver() {
        vm.date.observe(viewLifecycleOwner) { date ->
            onDateChangeObserver(date)
        }
    }

    private fun onDateChangeObserver(date: LocalDateTime) {
        vm.onDateChanged(date.toInstant(ZoneOffset.UTC).toEpochMilli())
            .observe(viewLifecycleOwner) { list ->
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

    private fun onSearchViewClick() = with(binding) {
        edSearch.addTextChangedListener(textWatcher())
        edSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) edSearch.clearFocus()
            false
        }
    }

    private fun textWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s?.isNotEmpty() == true) {
                vm.date.removeObservers(viewLifecycleOwner)
                binding.closeButton.visibility = View.VISIBLE
                vm.onSearchFocused("%$s%").observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            } else {
                binding.closeButton.visibility = View.GONE
                vm.onSearchFocused("%$s%").removeObservers(viewLifecycleOwner)
                listObserver()
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }

    private fun onCloseButtonClick() = with(binding) {
        closeButton.setOnClickListener {
            edSearch.setText("")
            edSearch.clearFocus()
            closeKeyboard()
            it.visibility = View.GONE
        }
    }

    private fun closeKeyboard() {
        val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.closeButton.windowToken, 0)
    }

    private fun onScrollView() = with(binding) {
        nestedScrollView.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {
                    datePickerBtn.hide()
                    newNoteBtn.hide()
                } else {
                    datePickerBtn.show()
                    newNoteBtn.show()
                }
            }
        )
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