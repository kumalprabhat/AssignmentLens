package com.learn.assignmentlens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learn.assignmentlens.R
import com.learn.assignmentlens.models.BodyModel

class BodyRecyclerAdapter(val bodyTexts: List<String>, val onItemClick:(position: Int) -> Unit): RecyclerView.Adapter<BodyRecyclerAdapter.BodyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.verticle_item_layout, parent, false)
        return BodyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bodyTexts.size
    }

    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {
        val bodyText = bodyTexts[position]
        holder.bodyTextView.text = bodyText
        holder.onClickChangeColorText.text = ""

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    inner class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bodyTextView: TextView = itemView.findViewById(R.id.bodyTextView)
        val onClickChangeColorText: TextView = itemView.findViewById(R.id.onClickChangeColorText)
    }
}