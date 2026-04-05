package com.ssbprep.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssbprep.R
import com.ssbprep.data.Article
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter @JvmOverloads constructor(
    private var articles: List<Article>,
    private var isBriefingMode: Boolean = false
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    @JvmOverloads
    fun updateData(newArticles: List<Article>, isBriefing: Boolean = false) {
        articles = newArticles
        isBriefingMode = isBriefing
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article, isBriefingMode)
    }

    override fun getItemCount(): Int = articles.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.newsImage)
        private val source: TextView = view.findViewById(R.id.newsSource)
        private val title: TextView = view.findViewById(R.id.newsTitle)
        private val description: TextView = view.findViewById(R.id.newsDescription)
        private val time: TextView = view.findViewById(R.id.newsTime)
        
        // Mentor Angle Views
        private val mentorLayout: View = view.findViewById(R.id.mentorInsightLayout)
        private val strategicText: TextView = view.findViewById(R.id.strategicImportanceText)
        private val ssbAngleText: TextView = view.findViewById(R.id.ssbAngleLeads)

        fun bind(article: Article, isBriefing: Boolean) {
            title.text = article.title
            description.text = article.description ?: ""
            source.text = article.source.name
            
            // Format time
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                val date = inputFormat.parse(article.publishedAt)
                val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                time.text = if (date != null) outputFormat.format(date) else article.publishedAt
            } catch (e: Exception) {
                time.text = article.publishedAt
            }

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .placeholder(R.color.glass_border)
                .centerCrop()
                .into(image)

            // SSB Mentor Curation Logic (Briefing Mode)
            if (isBriefing) {
                mentorLayout.visibility = View.VISIBLE
                
                // Simulated Curation based on Category Keywords
                val lowerTitle = article.title.lowercase()
                val lowerDesc = (article.description ?: "").lowercase()
                
                val importance: String
                val leads: String
                
                when {
                    lowerTitle.contains("defense") || lowerTitle.contains("army") || lowerTitle.contains("navy") || lowerTitle.contains("air force") -> {
                        importance = "Directly impacts India's defense modernization and tactical capabilities."
                        leads = "• Lecturette: Modernization of Armed Forces\n• GD: Indigenous vs Foreign procurement\n• Interview: Why join this branch given these changes?"
                    }
                    lowerTitle.contains("china") || lowerTitle.contains("pakistan") || lowerTitle.contains("border") -> {
                        importance = "Affects National Security and Border Management strategies."
                        leads = "• GD: Two-front war threat\n• Lecturette: India-China Border Issues\n• Interview: View on Line of Actual Control security."
                    }
                    lowerTitle.contains("economy") || lowerTitle.contains("growth") || lowerTitle.contains("budget") -> {
                        importance = "Influences defense budget allocation and self-reliance (Atmanirbharta)."
                        leads = "• GD: Economy vs Defense spending\n• Interview: Knowledge of India's current GDP and growth."
                    }
                    else -> {
                        importance = "Significant development in India's socio-economic or geopolitical landscape."
                        leads = "• GD: Impact on India's global image\n• Lecturette: Current events analysis"
                    }
                }
                
                strategicText.text = "Strategic Importance: $importance"
                ssbAngleText.text = leads
                description.maxLines = 2 // Shorter description to make room for mentor angle
            } else {
                mentorLayout.visibility = View.GONE
                description.maxLines = 4
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                itemView.context.startActivity(intent)
            }
        }
    }
}
