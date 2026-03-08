package com.ssbprep.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ssbprep.R
import com.ssbprep.databinding.ActivityOlqAssessmentBinding
import com.ssbprep.databinding.ItemOlqAssessmentBinding

data class OLQData(
    val name: String,
    val trainability: String,
    val importance: String, // High, Medium, Low
    val action: String
)

class OLQAssessmentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOlqAssessmentBinding
    private val assessmentItems = mutableListOf<ItemOlqAssessmentBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOlqAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener { finish() }
        binding.closeResultButton.setOnClickListener { 
            binding.resultOverlay.visibility = View.GONE 
        }
        
        setupAssessment()

        binding.analyseButton.setOnClickListener {
            performAnalysis()
        }
    }

    private fun setupAssessment() {
        val olqs = listOf(
            OLQData("Effective Intelligence", "High", "High", "Solve 2 practical 'What would you do?' puzzles daily."),
            OLQData("Reasoning Ability", "Medium", "High", "Read one editorial and summarize the logic in 3 points."),
            OLQData("Organizing Ability", "High", "Medium", "Plan your entire next week's schedule today."),
            OLQData("Power of Expression", "High", "Medium", "Speak for 2 mins in front of a mirror on a random topic."),
            OLQData("Social Adaptability", "Medium", "High", "Talk to 1 new person or a stranger today."),
            OLQData("Cooperation", "High", "High", "Volunteer to help a team member or family member in their task."),
            OLQData("Sense of Responsibility", "Low", "High", "Finish 100% of your pending tasks without being reminded."),
            OLQData("Initiative", "Medium", "Medium", "Take the lead in a small group task or volunteer first."),
            OLQData("Self-Confidence", "Medium", "High", "Face one minor fear or perform a task you're unsure about."),
            OLQData("Speed of Decision", "Medium", "High", "Practice making minor choices within 5 seconds."),
            OLQData("Ability to Influence", "Medium", "Medium", "Convince a friend to try a new habit using logic."),
            OLQData("Liveliness", "High", "Medium", "Keep a smile on your face even when doing a boring task."),
            OLQData("Determination", "Medium", "High", "Finish your workout or study session despite feeling tired."),
            OLQData("Courage", "Low", "High", "Admit a mistake openly or face a physical challenge."),
            OLQData("Stamina", "High", "Medium", "Add 10 more minutes to your physical or mental work sessions.")
        )

        olqs.forEach { olq ->
            val itemBinding = ItemOlqAssessmentBinding.inflate(LayoutInflater.from(this), binding.assessmentContainer, false)
            itemBinding.olqName.text = olq.name
            itemBinding.trainabilityText.text = "${olq.trainability} Trainability"
            itemBinding.actionText.text = olq.action
            itemBinding.root.tag = olq // Store data for analysis
            
            if (olq.trainability == "High") {
                itemBinding.trainabilityText.setTextColor(getColor(R.color.google_green))
            } else if (olq.trainability == "Low") {
                itemBinding.trainabilityText.setTextColor(getColor(R.color.google_red))
            }

            binding.assessmentContainer.addView(itemBinding.root)
            assessmentItems.add(itemBinding)
        }
    }

    private fun performAnalysis() {
        binding.loadingOverlay.visibility = View.VISIBLE
        val handler = Handler(Looper.getMainLooper())
        var progress = 0
        
        val runnable = object : Runnable {
            override fun run() {
                if (progress <= 100) {
                    val params = binding.progressBarFill.layoutParams as ConstraintLayout.LayoutParams
                    params.matchConstraintPercentWidth = progress / 100f
                    binding.progressBarFill.layoutParams = params
                    
                    val guidelineParams = binding.progressGuideline.layoutParams as ConstraintLayout.LayoutParams
                    guidelineParams.guidePercent = progress / 100f
                    binding.progressGuideline.layoutParams = guidelineParams
                    
                    binding.progressPercentage.text = "$progress%"
                    progress += 1
                    handler.postDelayed(this, 30)
                } else {
                    binding.loadingOverlay.visibility = View.GONE
                    showAnalysisResult()
                }
            }
        }
        handler.post(runnable)
    }

    private fun showAnalysisResult() {
        var lowCharacterCount = 0
        var mediumCount = 0
        var totalAnswered = 0
        
        assessmentItems.forEach { item ->
            val olq = item.root.tag as OLQData
            val selectedId = item.ratingGroup.checkedRadioButtonId
            if (selectedId != -1) {
                totalAnswered++
                val radioButton = item.root.findViewById<RadioButton>(selectedId)
                val rating = radioButton.text.toString()
                
                if (rating == "Low") {
                    if (olq.trainability == "Low" || olq.importance == "High") {
                        lowCharacterCount++
                    }
                } else if (rating == "Medium") {
                    mediumCount++
                }
            }
        }

        val title: String
        val message: String

        if (totalAnswered < 15) {
            title = "Incomplete Assessment"
            message = "Please rate yourself on all 15 qualities for a comprehensive analysis. Consistency is key in SSB."
        } else if (lowCharacterCount > 3) {
            title = "Focus on Character"
            message = "Your assessment suggests that certain core qualities (Factor II & IV) require deep introspection. These are often considered less trainable, so you must consciously align your daily actions with responsibility and courage. Start by taking ownership of small tasks at home."
        } else if (mediumCount > 7) {
            title = "Potential for Excellence"
            message = "You have a solid foundation, but many qualities are in the 'Medium' zone. To bridge the gap, focus on Factor I (The Mind) and Factor III (Social Effectiveness). Sharpen your decision-making speed and practice expressing your thoughts more clearly in group discussions."
        } else {
            title = "Strong Profile"
            message = "You demonstrate a strong alignment with Officer Like Qualities. Maintain this consistency. Focus on sustaining your liveliness and determination during long periods of stress, as seen in the 5-day SSB process."
        }

        binding.resultTitle.text = title
        binding.resultMessage.text = message
        binding.resultOverlay.visibility = View.VISIBLE
    }
}
