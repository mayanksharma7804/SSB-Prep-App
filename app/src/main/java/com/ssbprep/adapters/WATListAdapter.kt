package com.ssbprep.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssbprep.R

class WATListAdapter(private val onSetClick: (Int) -> Unit) :
    RecyclerView.Adapter<WATListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wat_set, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setId = position + 1
        holder.setTitle.text = "WAT Practice Set $setId"
        holder.itemView.setOnClickListener { onSetClick(setId) }
    }

    override fun getItemCount(): Int = 10

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val setTitle: TextView = view.findViewById(R.id.setTitleText)
    }
}