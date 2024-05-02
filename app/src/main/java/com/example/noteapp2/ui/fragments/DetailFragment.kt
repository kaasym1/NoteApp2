package com.example.noteapp2.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp2.App
import com.example.noteapp2.R
import com.example.noteapp2.databinding.FragmentDetailBinding
import com.example.noteapp2.models.NoteModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var noteId: Int = -1
    private var color: Int = 0
    private var timeText = ""
    private var dateText = ""
    private val notificationPermissionCode = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestNotificationPermission()

        update()

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = when (checkedId) {
                binding.radio1.id -> binding.radio1
                binding.radio2.id -> binding.radio2
                binding.radio3.id -> binding.radio3
                else -> binding.radio1
            }
            radioButton.let {
                when (it.tag) {
                    "red" -> color = resources.getColor(R.color.red)
                    "black" -> color = resources.getColor(R.color.black2)
                    "white" -> color = resources.getColor(R.color.white2)
                }
            }
        }

        val currentDate = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        dateText = dateFormat.format(currentDate)
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeText = timeFormat.format(currentDate)
        binding.txtTime.text = timeText
        binding.txtDate.text = dateText

        setupTextChangedListener()

        checkButtonVisibility()

        initListener()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val hasNotificationPermission = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECEIVE_BOOT_COMPLETED
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasNotificationPermission) {
                val dialog = AlertDialog.Builder(requireContext())
                    .setTitle("Разрешение на отправку уведомлений")
                    .setMessage("Разрешить приложению отправлять уведомления?")
                    .setPositiveButton("Да") { dialog, which ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            notificationPermissionCode
                        )
                    }
                    .setNegativeButton("Нет") { dialog, which ->
                    }
                    .create()
                dialog.show()
            }
        }
    }

    private fun update() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val argNote = App.appDataBase?.noteDao()?.getNoteById(noteId)
            argNote?.let {
                binding.etTitle.setText(it.title)
                binding.etDescription.setText(it.content)
            }
        }
    }

    private fun initListener() {
        binding.btnFinished.setOnClickListener {
            val noteModel = NoteModel(
                title = binding.etTitle.text.toString(),
                content = binding.etDescription.text.toString(),
                color = color,
                time = timeText,
                date = dateText
            )

            if (noteId != -1){
                val updatedNote = NoteModel(
                    title = binding.etTitle.text.toString(),
                    content = binding.etDescription.text.toString(),
                    color = color,
                    time = timeText,
                    date = dateText
                )
                updatedNote.id = noteId
                App.appDataBase?.noteDao()?.updateNote(updatedNote)
            }else{
                App.appDataBase?.noteDao()?.insertNote(noteModel)
            }
            findNavController().navigate(R.id.home_fragment)
        }
    }

    private fun setupTextChangedListener() {
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkButtonVisibility()
            }
        })

        binding.etDescription.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkButtonVisibility()
            }
        })
    }

    private fun checkButtonVisibility() {
        val titleText = binding.etTitle.text.toString().trim()
        val descriptionText = binding.etDescription.text.toString().trim()

        binding.btnFinished.visibility =
            if (titleText.isNotEmpty() && descriptionText.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}
