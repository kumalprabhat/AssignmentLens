package com.learn.assignmentlens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.assignmentlens.R

class HeaderAdapter(var headerText: List<String>): RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.header_item_layout, parent, false)
        return HeaderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return headerText.size
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        val headerText = headerText[position]
        holder.headerTextView.text = headerText
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val headerTextView: TextView = itemView.findViewById(R.id.headerTextView)
    }
}