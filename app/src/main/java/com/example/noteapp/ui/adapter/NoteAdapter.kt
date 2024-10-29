package com.example.noteapp.ui.adapter

import NoteModel
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.interfaces.OnClickItem

class NoteAdapter(private val onLongClick: OnClickItem, private val onClick: OnClickItem) : ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) {
            binding.itemTitle.text = item.title
            binding.itemDescription.text = item.description
            binding.itemDate.text = item.date
            binding.itemTime.text = item.time

            val background: Int
            val title: Int
            val description: Int
            val date: Int
            val time: Int

            val context = binding.root.context

            when (item.selectedColor) {
                "white" -> {
                    background = R.drawable.bg_item_white
                    title = R.color.beige
                    description = R.color.beige
                    date = R.color.beige
                    time = R.color.beige
                }
                "red" -> {
                    background = R.drawable.bg_item_red
                    title = R.color.orange
                    description = R.color.orange
                    date = R.color.orange
                    time = R.color.orange
                }
                else -> {
                    background = R.drawable.bg_item_black
                    title = R.color.white
                    description = R.color.white
                    date = R.color.white
                    time = R.color.white
                }
            }

            binding.root.background = ContextCompat.getDrawable(context, background)
            binding.itemTitle.setTextColor(ContextCompat.getColor(context, title))
            binding.itemDescription.setTextColor(ContextCompat.getColor(context, description))
            binding.itemDate.setTextColor(ContextCompat.getColor(context, date))
            binding.itemTime.setTextColor(ContextCompat.getColor(context, time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnLongClickListener{
            onLongClick.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener{
            onClick.onClick(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }
    }
}