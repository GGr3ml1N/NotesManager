package com.ggr3ml1n.notesmanager.presentation.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ggr3ml1n.notesmanager.databinding.ActivityMainBinding

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