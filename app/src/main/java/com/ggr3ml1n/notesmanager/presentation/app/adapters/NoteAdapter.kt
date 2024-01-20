package com.ggr3ml1n.notesmanager.presentation.app.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ggr3ml1n.domain.entities.NoteDomain
import com.ggr3ml1n.notesmanager.R
import com.ggr3ml1n.notesmanager.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class NoteAdapter(private val listener: Touchable): ListAdapter<NoteDomain, NoteAdapter.ItemHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.create(parent)


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : ViewHolder(view) {

        private val binding: NoteItemBinding = NoteItemBinding.bind(view)

        fun setData(note: NoteDomain, listener: Touchable) = with(binding) {

            tvName.text = note.name
            tvTime.text = StringBuilder(timeToString(note.dateStart.time))
                .append(" - ")
                .append(timeToString(note.dateFinish.time))

            linearLayout.setOnClickListener {
                listener.onClick(note)
            }

            imDelete.setOnClickListener{
                listener.onDelete(note)
            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun timeToString(millis: Long) : String {
            val formatter = SimpleDateFormat("HH:mm").apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            return formatter.format(Date(millis))
        }
        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.note_item,
                        parent,
                        false
                    )
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<NoteDomain>() {

        override fun areItemsTheSame(oldItem: NoteDomain, newItem: NoteDomain): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteDomain, newItem: NoteDomain): Boolean =
            oldItem == newItem

    }

    interface Touchable {
        fun onClick(note: NoteDomain)

        fun onDelete(note: NoteDomain)
    }
}