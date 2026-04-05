package com.ssbprep.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssbprep.databinding.ActivityLecturetteTopicsBinding

class LecturetteTopicsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLecturetteTopicsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLecturetteTopicsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }

        setupTopics()
    }

    private fun setupTopics() {
        val highLevel = listOf(
            "Venture capital", "Globalization", "Urbanization", "Arms race", "Black market",
            "International relations", "Indian Civil Services", "Educational reforms",
            "River Linking", "Weapons of Mass destruction", "Green hydrogen", "Global warming",
            "INDIA-MIDDLE EAST RELATION", "INSTC", "India and its neighbours", "Anti-china issues",
            "Belt and road initiative", "India China Relations", "Tariff", "National Education Policy"
        )

        val moderateLevel = listOf(
            "Indian national army", "Cyber bullying", "Cloud kitchen", "Air force helicopters",
            "Future next generation fighter aircraft", "Alternate education", "Counselling",
            "Cosmetic Surgery", "AI", "5th gen..Fighter", "E-commerce", "Chandrayaan 3",
            "Nuclear energy", "Agniveer scheme", "INDIAN EDUCATION SYSTEM", "cyber spaying",
            "FATA", "impact of social media on youth", "Law enforcement in India",
            "Web entertainments", "Coding for kids", "Soft and hard skills", "Swatch bharat mission",
            "NCC", "Gender equality", "Social work"
        )

        val lowLevel = listOf(
            "Smart phone", "Role of Sports in youth development", "GOOGLE", "MY FAVORITE BOOK",
            "Ambition vs passion", "National Game of India", "Festivals", "Creativity",
            "My Best Friend"
        )

        binding.highLevelTopics.text = highLevel.joinToString("\n") { "• $it" }
        binding.moderateLevelTopics.text = moderateLevel.joinToString("\n") { "• $it" }
        binding.lowLevelTopics.text = lowLevel.joinToString("\n") { "• $it" }
    }
}
