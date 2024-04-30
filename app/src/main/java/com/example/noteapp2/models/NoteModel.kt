package com.example.noteapp2.models

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val content: String,
    val color: Int,
    val time: String,
    val date: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}