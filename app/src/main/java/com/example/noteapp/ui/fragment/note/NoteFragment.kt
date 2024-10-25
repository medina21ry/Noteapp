package com.example.noteapp.ui.fragment.note
import NoteModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.interfaces.OnClickItem
import com.example.noteapp.ui.adapter.NoteAdapter
import com.example.noteapp.utils.SharedPreferenceHelper

class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteAdapter: NoteAdapter
    private var isGridLayout: Boolean = false
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        isGridLayout = sharedPreferenceHelper.getIsGridLayout()
        noteAdapter = NoteAdapter(onLongClick = this, onClick = this)
        initialize()
        setUpListeners()
        getData()
        updateLayoutButtonIcon()
    }

    private fun initialize() {
        binding.rvNote.apply {
            layoutManager = if (isGridLayout) {
                GridLayoutManager(requireContext(), 2)
            } else {
                LinearLayoutManager(requireContext())
            }
            adapter = noteAdapter
        }
    }

    private fun setUpListeners() = with(binding) {
        addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_detailNoteFragment)
        }
        gridLayout.setOnClickListener {
            isGridLayout = true
            binding.rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
            sharedPreferenceHelper.setIsGridLayout(isGridLayout)
            updateLayoutButtonIcon()
        }
        linerLayout.setOnClickListener {
            isGridLayout = false
            binding.rvNote.layoutManager = LinearLayoutManager(requireContext())
            sharedPreferenceHelper.setIsGridLayout(isGridLayout)
            updateLayoutButtonIcon()
        }
    }

    private fun updateLayoutButtonIcon() = with(binding) {
        if (isGridLayout) {
            gridLayout.visibility = View.GONE
            linerLayout.visibility = View.VISIBLE
        } else {
            gridLayout.visibility = View.VISIBLE
            linerLayout.visibility = View.GONE
        }
    }

    private fun getData() {
        App().getInstance()?.noteDao()?.getAll()?.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Вы точно хотите удалить эту заметку?")
            setPositiveButton("Да") { dialog, which ->
                App().getInstance()?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Нет") { dialog, which ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToDetailNoteFragment(noteModel.id)
        findNavController().navigate(action)
    }
}