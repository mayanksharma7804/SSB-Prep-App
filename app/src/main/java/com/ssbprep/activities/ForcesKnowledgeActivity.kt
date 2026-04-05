package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.databinding.ActivityForcesKnowledgeBinding

class ForcesKnowledgeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForcesKnowledgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForcesKnowledgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        binding.armyCard.setOnClickListener {
            openServiceDetail("Army")
        }

        binding.airForceCard.setOnClickListener {
            openServiceDetail("AirForce")
        }

        binding.navyCard.setOnClickListener {
            openServiceDetail("Navy")
        }

        binding.missileCard.setOnClickListener {
            openServiceDetail("Missiles")
        }

        binding.awardsCard.setOnClickListener {
            openServiceDetail("Awards")
        }

        binding.generalCard.setOnClickListener {
            openServiceDetail("General")
        }

        binding.medicalCard.setOnClickListener {
            openServiceDetail("Medical")
        }
    }

    private fun openServiceDetail(service: String) {
        val intent = Intent(this, ServiceDetailActivity::class.java)
        intent.putExtra("SERVICE_TYPE", service)
        startActivity(intent)
    }
}
