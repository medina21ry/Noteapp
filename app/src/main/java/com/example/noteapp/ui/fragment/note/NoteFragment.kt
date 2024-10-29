package com.example.noteapp.ui.fragment.note

import NoteModel
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.interfaces.OnClickItem
import com.example.noteapp.ui.adapter.NoteAdapter

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter(onClick = this, onLongClick = this)
    private var isGridLayout = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        initialize()
        getData()
    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun setupListener() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
        btnChangeLayout.setOnClickListener {
            toggleLayout()
        }
    }

    private fun toggleLayout() {
        isGridLayout = !isGridLayout
        val layoutManager = if (isGridLayout) {
            GridLayoutManager(requireContext(), 2)
        } else {
            LinearLayoutManager(requireContext())
        }
        binding.rvNote.layoutManager = layoutManager
        updateLayoutChangerIcon()
    }

    private fun updateLayoutChangerIcon() {
        val iconResId = if (isGridLayout) {
            R.drawable.ic_linear_layout
        } else {
            R.drawable.ic_grid_layout
        }
        binding.btnChangeLayout.setImageResource(iconResId)
    }

    private fun getData() {
        App.getDatabase().noteDao().getAll().observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Are you sure you want to delete?")
            setPositiveButton("Yes") { dialog, which ->
                App.getDatabase().noteDao().deleteNote(noteModel)
            }
            setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToDetailNoteFragment(noteModel.id)
        findNavController().navigate(action)
    }
}
