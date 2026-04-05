package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.ssbprep.R

class TATPreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tat_preview)

        val setId = intent.getIntExtra("SET_ID", 1)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val setTitle: TextView = findViewById(R.id.setTitle)
        setTitle.text = "TAT Practice Set $setId"

        val startButton: MaterialButton = findViewById(R.id.startTestButton)
        startButton.setOnClickListener {
            val intent = Intent(this, TATSimulatorActivity::class.java)
            intent.putExtra("SET_ID", setId)
            startActivity(intent)
            finish()
        }
    }
}