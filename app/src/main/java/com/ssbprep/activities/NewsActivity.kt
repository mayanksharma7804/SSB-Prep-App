package com.ssbprep.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssbprep.adapters.NewsAdapter
import com.ssbprep.data.NewsApiService
import com.ssbprep.data.NewsResponse
import com.ssbprep.databinding.ActivityNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsApiService: NewsApiService
    private val NEWS_API_KEY = "aa6bb98039f7474fa22a95c046d4c715"

    // Focused on Indian newspapers: The Hindu, TOI, Indian Express
    private val DEFAULT_QUERY = "Indian Defence OR Army OR Navy OR \"Air Force\""
    private val INDIAN_DOMAINS = "timesofindia.indiatimes.com,thehindu.com,indianexpress.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        setupRecyclerView()
        setupRetrofit()
        setupSearch()
        setupSwipeRefresh()

        // Initial fetch
        fetchNews(DEFAULT_QUERY, "publishedAt")
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(emptyList())
        binding.allNewsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.allNewsRecyclerView.adapter = newsAdapter
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsApiService = retrofit.create(NewsApiService::class.java)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val query = binding.searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                fetchNews(query, "publishedAt")
            } else {
                fetchNews(DEFAULT_QUERY, "publishedAt")
            }
        }
        
        binding.swipeRefreshLayout.setColorSchemeResources(
            com.google.android.material.R.color.design_default_color_primary,
            android.R.color.holo_blue_dark
        )
    }

    private fun setupSearch() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    fetchNews(query, "publishedAt")
                }
                true
            } else {
                false
            }
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    fetchNews(DEFAULT_QUERY, "publishedAt")
                }
            }
        })
    }

    private fun fetchNews(query: String, sortBy: String) {
        if (!binding.swipeRefreshLayout.isRefreshing) {
            binding.searchProgress.visibility = View.VISIBLE
        }
        binding.noResultsText.text = "Fetching latest updates..."
        binding.noResultsText.visibility = View.VISIBLE

        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -14) // 2 weeks for high relevance
        val fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime())

        newsApiService.getEverything(query, fromDate, sortBy, "en", NEWS_API_KEY, INDIAN_DOMAINS)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                    if (isFinishing || isDestroyed) return
                    binding.searchProgress.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                    
                    if (response.isSuccessful && response.body() != null) {
                        val articles = response.body()!!.articles
                        if (articles.isEmpty()) {
                            binding.noResultsText.text = "No relevant news found at the moment."
                            binding.noResultsText.visibility = View.VISIBLE
                        } else {
                            binding.noResultsText.visibility = View.GONE
                        }
                        newsAdapter.updateData(articles)
                    } else {
                        binding.noResultsText.text = "Unable to load news. Please try again."
                        binding.noResultsText.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    if (isFinishing || isDestroyed) return
                    binding.searchProgress.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.noResultsText.text = "Network error. Check your connection."
                    binding.noResultsText.visibility = View.VISIBLE
                }
            })
    }
}
