package com.example.noteapp.ui.fragment.note

import NoteModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentDetailNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailNoteFragment : Fragment() {

    private lateinit var binding: FragmentDetailNoteBinding
    private var selectedColor: String = "black"
    private var noteId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        currentDateTime()
        setupRadioGroupListener()
        update()
        textWatcher()
    }

    private fun update() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val noteDao = App().getInstance()?.noteDao()
            noteDao?.getNoteById(noteId)?.let { model ->
                binding.etTitle.setText(model.title)
                binding.etDescription.setText(model.description)
                selectedColor = model.selectedColor
                updateBackgroundColor()
            }
        }
    }

    private fun setupListener() {
        binding.btnAddText.setOnClickListener {
            val etTitle = binding.etTitle.text.toString()
            val etDescription = binding.etDescription.text.toString()
            val currentDate = binding.tvDate.text.toString()
            val currentTime = binding.tvTime.text.toString()

            if (noteId != -1) {
                val updateNote = NoteModel(etTitle, etDescription, currentDate, currentTime, selectedColor)
                updateNote.id = noteId
                App().getInstance()?.noteDao()?.updateNote(updateNote)
            } else {
                App().getInstance()?.noteDao()?.insertNote(NoteModel(etTitle, etDescription, currentDate, currentTime, selectedColor))
            }
            findNavController().navigate(R.id.action_detailNoteFragment_to_noteFragment)
        }

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun currentDateTime() {
        val currentDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
        binding.tvDate.text = currentDate

        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        binding.tvTime.text = currentTime
    }

    private fun setupRadioGroupListener() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedColor = when (checkedId) {
                R.id.radio_btn_black -> "black"
                R.id.radio_btn_white -> "white"
                R.id.radio_btn_red -> "red"
                else -> "black"
            }
            updateBackgroundColor()
        }
    }

    private fun updateBackgroundColor() {
        val context = requireContext()
        val colorResId = when (selectedColor) {
            "white" -> {
                binding.etDescription.setTextColor(ContextCompat.getColor(context, R.color.beige))
                binding.etTitle.setTextColor(ContextCompat.getColor(context, R.color.beige))
                binding.tvDate.setTextColor(ContextCompat.getColor(context, R.color.beige))
                binding.tvTime.setTextColor(ContextCompat.getColor(context, R.color.beige))
                R.color.whiteRadio
            }
            "red" -> {
                binding.etDescription.setTextColor(ContextCompat.getColor(context, R.color.orange))
                binding.etTitle.setTextColor(ContextCompat.getColor(context, R.color.orange))
                binding.tvDate.setTextColor(ContextCompat.getColor(context, R.color.orange))
                binding.tvTime.setTextColor(ContextCompat.getColor(context, R.color.orange))
                R.color.redRadio
            }
            else -> {
                binding.etTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.etDescription.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.tvDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.tvTime.setTextColor(ContextCompat.getColor(context, R.color.white))
                R.color.blackRadio
            }
        }
        binding.root.setBackgroundColor(ContextCompat.getColor(context, colorResId))
    }

    private fun textWatcher() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = binding.etTitle.text.isNotEmpty()
                val description = binding.etDescription.text.isNotEmpty()
                binding.btnAddText.visibility =
                    if (title && description) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etTitle.addTextChangedListener(textWatcher)
        binding.etDescription.addTextChangedListener(textWatcher)
    }
}