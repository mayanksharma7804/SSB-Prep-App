package com.ssbprep.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.databinding.ActivityInterviewTipsBinding

class InterviewTipsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInterviewTipsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterviewTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }
    }
}
