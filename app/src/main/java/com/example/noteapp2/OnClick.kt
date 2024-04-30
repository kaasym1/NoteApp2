package com.example.noteapp2

import com.example.noteapp2.models.NoteModel

interface OnClick {
    fun onItemClick(noteModel: NoteModel)
}