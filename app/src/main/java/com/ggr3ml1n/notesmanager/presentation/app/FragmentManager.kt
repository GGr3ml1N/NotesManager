package com.ggr3ml1n.notesmanager.presentation.app

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ggr3ml1n.notesmanager.R

object FragmentManager {

    var currentFrag: Fragment? = null

    fun setFragment(activity: AppCompatActivity, fragment: Fragment) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeholder, fragment)
        transaction.commit()
        currentFrag = fragment
    }
}