package com.example.noteapp2.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp2.App
import com.example.noteapp2.OnClick
import com.example.noteapp2.R
import com.example.noteapp2.databinding.FragmentHomeBinding
import com.example.noteapp2.models.NoteModel
import com.example.noteapp2.ui.adapters.NoteAdapter


class HomeFragment : Fragment(), OnClick {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NoteAdapter
    private var flag = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(this)
        val list = App.appDataBase?.noteDao()?.getAllNotes()
        binding.rvNotes.adapter = adapter
        adapter.submitList(list)
        initListener()
    }

  

    override fun onResume() {
        super.onResume()
        updateNoteList()
    }

    private fun initListener() = with(binding) {
        btnPlus.setOnClickListener {
            findNavController().navigate(R.id.detail_fragment)
        }

        imgShape.setOnClickListener {
            if (flag) {
                imgShape.setImageResource(R.drawable.ic_menu)
                binding.rvNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                flag = false
            } else {
                imgShape.setImageResource(R.drawable.ic_shape2)
                binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
                flag = true
            }
        }
    }

    private fun updateNoteList() {
        val notes = App().getInstance()?.noteDao()?.getAllNotes()
        adapter.submitList(notes)
    }

    override fun onItemClick(noteModel: NoteModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
            noteModel.id
        )
        findNavController().navigate(action)
    }
}