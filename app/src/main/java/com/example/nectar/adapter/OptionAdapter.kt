// File: app/src/main/java/com/example/nectar/adapter/OptionAdapter.kt
package com.example.nectar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nectar.R
import com.example.nectar.model.OptionItem

class OptionsAdapter(
    private val items: List<OptionItem>,
    private val onItemClick: (OptionItem) -> Unit
) : RecyclerView.Adapter<OptionsAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.option_icon)
        private val title: TextView = itemView.findViewById(R.id.option_text)

        fun bind(item: OptionItem) {
            icon.setImageResource(item.iconResId)
            title.text = item.title
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}