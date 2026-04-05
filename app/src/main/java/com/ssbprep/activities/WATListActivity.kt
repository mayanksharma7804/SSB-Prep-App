package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssbprep.R
import com.ssbprep.adapters.WATListAdapter

class WATListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wat_list)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val recyclerView: RecyclerView = findViewById(R.id.watRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WATListAdapter { setId ->
            val intent = Intent(this, WATPreviewActivity::class.java)
            intent.putExtra("SET_ID", setId)
            startActivity(intent)
        }
    }
}