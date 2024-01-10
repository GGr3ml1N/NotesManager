package com.ggr3ml1n.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ggr3ml1n.data.entities.NoteData

@Database(entities = [NoteData::class], version = 1)
abstract class MainDataBase : RoomDatabase() {

    abstract fun getDAO(): DAO

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = MainDataBase::class.java,
                    name = "NotesManager.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}