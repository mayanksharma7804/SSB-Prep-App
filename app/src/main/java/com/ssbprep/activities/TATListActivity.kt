package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssbprep.R
import com.ssbprep.adapters.TATListAdapter

class TATListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tat_list)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val recyclerView: RecyclerView = findViewById(R.id.tatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TATListAdapter { setId ->
            val intent = Intent(this, TATPreviewActivity::class.java)
            intent.putExtra("SET_ID", setId)
            startActivity(intent)
        }
    }
}