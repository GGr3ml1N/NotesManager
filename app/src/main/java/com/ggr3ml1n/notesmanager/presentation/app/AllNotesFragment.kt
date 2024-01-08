package com.ggr3ml1n.notesmanager.presentation.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ggr3ml1n.notesmanager.R

class AllNotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_notes, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllNotesFragment()
    }
}