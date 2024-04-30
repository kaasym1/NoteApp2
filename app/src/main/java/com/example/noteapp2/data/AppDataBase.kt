package com.example.noteapp2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp2.models.NoteModel

@Database(entities = [NoteModel::class], version = 1)

abstract class AppDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}