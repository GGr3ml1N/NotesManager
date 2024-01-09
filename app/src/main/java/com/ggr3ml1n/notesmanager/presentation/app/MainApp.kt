package com.ggr3ml1n.notesmanager.presentation.app

import android.app.Application
import com.ggr3ml1n.notesmanager.data.database.MainDataBase

class MainApp : Application() {
    val dataBase by lazy { MainDataBase.getDataBase(this) }
}