package com.ggr3ml1n.notesmanager.presentation.app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ggr3ml1n.notesmanager.R
import com.ggr3ml1n.notesmanager.presentation.vm.CurrentNoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentNoteActivity : AppCompatActivity() {

    private val vm: CurrentNoteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_note)
    }
}