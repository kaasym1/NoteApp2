package com.example.noteapp2.ui.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp2.R
import com.example.noteapp2.databinding.FragmentChatBinding
import com.example.noteapp2.ui.adapters.ChatAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore


class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private val db = Firebase.firestore
    private val chatAdapter = ChatAdapter()
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListener()

        val fireStore = db.collection("users")

        listenerRegistration = fireStore.addSnapshotListener { querySnapshot, exception ->
            if (exception != null) {
                Log.d(TAG, "Listen failed.", exception)
                return@addSnapshotListener
            }

            val userList = mutableListOf<String>()
            querySnapshot?.documents?.forEach { document ->
                val name = document.getString("name")
                name?.let {
                    userList.add(it)
                }
            }
            chatAdapter.submitList(userList)
        }
    }

    private fun initialize() {
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
        }
    }

    private fun setupListener() {
        binding.btnChat.setOnClickListener {
            val user = hashMapOf("name" to binding.etChat.text.toString())
            db.collection("users").add(user).addOnSuccessListener {
                Log.d(TAG, "User added successfully")
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Failed to add user: $exception")
            }
            binding.etChat.text.clear()
        }
    }
}