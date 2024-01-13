package com.ggr3ml1n.notesmanager.presentation.app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.notesmanager.databinding.ActivityMainBinding
import com.ggr3ml1n.notesmanager.presentation.app.fragments.AllNotesFragment
import com.ggr3ml1n.notesmanager.presentation.app.utils.FragmentManager

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentManager.setFragment(this, AllNotesFragment.newInstance())
    }
}