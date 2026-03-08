package com.ssbprep.adapters

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.ssbprep.R
import com.ssbprep.databinding.ItemKnowledgeExpandableBinding
import com.ssbprep.databinding.ItemRankFlowBinding

data class KnowledgeSubItem(
    val name: String,
    val details: String,
    val mapUrl: String? = null
)

data class KnowledgeCategory(
    val title: String,
    val description: String? = null,
    val subItems: List<KnowledgeSubItem> = emptyList(),
    val rankGroups: Map<String, List<String>>? = null,
    val selectableItems: Map<String, String>? = null, // For Commands: Name -> Details
    var isExpanded: Boolean = false,
    var selectedTab: String? = null
)

class KnowledgeExpandableAdapter(private val categories: List<KnowledgeCategory>) :
    RecyclerView.Adapter<KnowledgeExpandableAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKnowledgeExpandableBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKnowledgeExpandableBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.categoryTitle.text = category.title
        
        // Setup Description
        if (!category.description.isNullOrEmpty()) {
            holder.binding.contentText.visibility = View.VISIBLE
            holder.binding.contentText.text = category.description
        } else {
            holder.binding.contentText.visibility = View.GONE
        }
        
        // Setup Chips (Tabs)
        holder.binding.chipGroup.removeAllViews()
        val hasTabs = category.rankGroups != null || category.selectableItems != null
        
        if (hasTabs) {
            holder.binding.chipGroup.visibility = View.VISIBLE
            val keys = category.rankGroups?.keys ?: category.selectableItems?.keys ?: emptySet()
            keys.forEach { tabName ->
                val chip = Chip(holder.binding.root.context).apply {
                    text = tabName
                    isCheckable = true
                    isChecked = category.selectedTab == tabName
                    setOnClickListener {
                        category.selectedTab = if (category.selectedTab == tabName) null else tabName
                        notifyItemChanged(position)
                    }
                }
                holder.binding.chipGroup.addView(chip)
            }
        } else {
            if (category.rankGroups == null && category.selectableItems == null) {
                holder.binding.chipGroup.visibility = View.GONE
            }
        }

        // Setup Dynamic Content
        val subItemsContainer = holder.binding.expandableContent
        val staticChildCount = 3 // divider, contentText, chipGroup
        if (subItemsContainer.childCount > staticChildCount) {
            subItemsContainer.removeViews(staticChildCount, subItemsContainer.childCount - staticChildCount)
        }

        if (category.selectedTab != null) {
            if (category.rankGroups?.containsKey(category.selectedTab) == true) {
                // Show Rank Flow
                val ranks = category.rankGroups[category.selectedTab] ?: emptyList()
                ranks.forEachIndexed { index, rank ->
                    val rankBinding = ItemRankFlowBinding.inflate(
                        LayoutInflater.from(holder.binding.root.context), subItemsContainer, false
                    )
                    rankBinding.rankName.text = rank
                    rankBinding.arrowDown.visibility = if (index == ranks.size - 1) View.GONE else View.VISIBLE
                    subItemsContainer.addView(rankBinding.root)
                }
            } else if (category.selectableItems?.containsKey(category.selectedTab) == true) {
                // Show Selectable Item Details (e.g., Command Info)
                val details = category.selectableItems[category.selectedTab] ?: ""
                val detailsView = TextView(holder.binding.root.context).apply {
                    text = details
                    setTextColor(holder.binding.root.context.getColor(R.color.text_secondary))
                    textSize = 14f
                    setPadding(0, 16, 0, 8)
                    setLineSpacing(0f, 1.2f)
                }
                subItemsContainer.addView(detailsView)
            }
        } else if (category.subItems.isNotEmpty()) {
            // Setup SubItems
            category.subItems.forEach { subItem ->
                val titleView = TextView(holder.binding.root.context).apply {
                    val spannable = SpannableString("${subItem.name}:")
                    spannable.setSpan(StyleSpan(Typeface.BOLD), 0, spannable.length, 0)
                    text = spannable
                    setTextColor(holder.binding.root.context.getColor(R.color.primary_blue))
                    textSize = 15f
                    setPadding(0, 24, 0, 4)
                }
                val detailsView = TextView(holder.binding.root.context).apply {
                    text = subItem.details
                    setTextColor(holder.binding.root.context.getColor(R.color.text_secondary))
                    textSize = 14f
                    setPadding(0, 0, 0, 8)
                    setLineSpacing(0f, 1.2f)
                }
                subItemsContainer.addView(titleView)
                subItemsContainer.addView(detailsView)

                if (subItem.mapUrl != null) {
                    val mapView = TextView(holder.binding.root.context).apply {
                        text = "📍 View on Google Maps"
                        setTextColor(holder.binding.root.context.getColor(R.color.google_blue))
                        setPadding(0, 0, 0, 16)
                        setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(subItem.mapUrl))
                            context.startActivity(intent)
                        }
                    }
                    subItemsContainer.addView(mapView)
                }
            }
        }

        // Handle Expansion
        holder.binding.expandableContent.visibility = if (category.isExpanded) View.VISIBLE else View.GONE
        holder.binding.expandIcon.rotation = if (category.isExpanded) 90f else 0f

        holder.binding.itemHeader.setOnClickListener {
            category.isExpanded = !category.isExpanded
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = categories.size
}
