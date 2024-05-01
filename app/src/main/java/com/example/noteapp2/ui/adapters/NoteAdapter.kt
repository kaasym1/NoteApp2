package com.example.noteapp2.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp2.OnClick
import com.example.noteapp2.databinding.ItemNotesBinding
import com.example.noteapp2.models.NoteModel

class NoteAdapter(private val onClick: OnClick) :
    ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNotesBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.onItemClick(item)
        }
    }


    inner class ViewHolder(private val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(noteModel: NoteModel) {
            binding.apply {
                tvTitle.text = noteModel.title
                tvDescription.text = noteModel.content
                tvData.text = noteModel.date
                tvTime.text = noteModel.time
                itemView.setBackgroundColor(noteModel.color)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.title == newItem.title && oldItem.content == newItem.content
        }
    }
}