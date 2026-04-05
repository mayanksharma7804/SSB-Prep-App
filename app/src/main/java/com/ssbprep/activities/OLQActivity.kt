package com.ssbprep.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssbprep.adapters.KnowledgeCategory
import com.ssbprep.adapters.KnowledgeExpandableAdapter
import com.ssbprep.adapters.KnowledgeSubItem
import com.ssbprep.databinding.ActivityOlqBinding

class OLQActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOlqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOlqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleText.text = "Officer Like Qualities"
        binding.backButton.setOnClickListener { finish() }

        val categories = getOLQCategories()
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = KnowledgeExpandableAdapter(categories)

        binding.analyzeButton.setOnClickListener {
            startActivity(Intent(this, OLQAssessmentActivity::class.java))
        }
    }

    private fun getOLQCategories(): List<KnowledgeCategory> {
        return listOf(
            KnowledgeCategory(
                "What are OLQs?",
                "Officer Like Qualities (OLQs) are the 15 personality traits that the SSB looks for in a candidate. These are the building blocks of an effective leader in the Armed Forces.",
                subItems = listOf(
                    KnowledgeSubItem("Importance", "In the military, leadership is about character, not just commands. OLQs ensure that you can lead men into battle, manage stress, and make ethical decisions under pressure."),
                    KnowledgeSubItem("Measurement", "These are measured through three different lenses: Psychology (Manasa), GTO (Karmana), and Interview (Vacha).")
                )
            ),
            KnowledgeCategory(
                "Factor I: Planning and Organizing",
                "The Mind/Head - Intellectual capacity to solve real-world problems.",
                selectableItems = mapOf(
                    "Effective Intelligence" to "The ability to solve practical, day-to-day problems with available resources. It's 'jugaad' mixed with common sense.\n\nSSB Task: GTO obstacles, PPDT stories.\nDevelopment: Solve daily life problems yourself instead of asking for help.",
                    "Reasoning Ability" to "The ability to grasp essentials and arrive at logical conclusions. Includes receptivity and questioning 'why'.\n\nSSB Task: SRT, OIR, Interview.\nDevelopment: Read editorials, ask 'why' things happen the way they do.",
                    "Organizing Ability" to "Capacity to arrange people and material systematically to produce results.\n\nSSB Task: Group Tasks, Command Task.\nDevelopment: Organize family events or college fests.",
                    "Power of Expression" to "Putting across ideas clearly and concisely. It's about being understood, not about fancy English.\n\nSSB Task: Lecturette, GD, Interview.\nDevelopment: Speak in front of a mirror, practice summarizing long articles."
                )
            ),
            KnowledgeCategory(
                "Factor II: Social Adjustment",
                "The Heart - How well you blend into a team and your reliability.",
                selectableItems = mapOf(
                    "Social Adaptability" to "Adapting to wide variety of environments and people (seniors/peers/subordinates).\n\nSSB Task: Living in lines, Group tasks.\nDevelopment: Talk to 1 new person daily, step out of your comfort zone.",
                    "Cooperation" to "Participating willingly in harmony to achieve a group goal. 'We' over 'I'.\n\nSSB Task: GPE, PGT, HGT.\nDevelopment: Participate in team sports or group projects.",
                    "Sense of Responsibility" to "Understanding duty and performing it without being told. Reliability is key.\n\nSSB Task: Following rules, Individual tasks.\nDevelopment: Take ownership of chores at home without reminders."
                )
            ),
            KnowledgeCategory(
                "Factor III: Social Effectiveness",
                "The Guts/Spirit - Your impact on the group and leadership presence.",
                selectableItems = mapOf(
                    "Initiative" to "Originate action in the right direction and sustain it until the end.\n\nSSB Task: GD, GPE, PGT.\nDevelopment: Volunteer for tasks no one else wants to do.",
                    "Self-Confidence" to "Faith in your own abilities to meet stressful or unfamiliar situations.\n\nSSB Task: Command Task, Individual Obstacles.\nDevelopment: Prepare well for tasks and face your fears.",
                    "Speed of Decision" to "Arriving at a workable decision quickly. A quick 'okay' decision is better than a late 'perfect' one.\n\nSSB Task: GPE, Command Task.\nDevelopment: Practice making small daily decisions faster.",
                    "Ability to Influence" to "Convincing others to follow your lead through logic and character.\n\nSSB Task: Group Discussion, GPE.\nDevelopment: Lead with logic and empathy.",
                    "Liveliness" to "Remaining cheerful and buoyant even under stress. A smiling leader prevents panic.\n\nSSB Task: Entire 5-day stay.\nDevelopment: Find humor in tough situations, stay positive."
                )
            ),
            KnowledgeCategory(
                "Factor IV: Dynamic Qualities",
                "The Limbs - Mental and physical robustness.",
                selectableItems = mapOf(
                    "Determination" to "Refusal to quit despite setbacks or exhaustion. Mental grit.\n\nSSB Task: Snake Race, Individual Obstacles.\nDevelopment: Finish what you start, no matter how hard.",
                    "Courage" to "Appreciating a risk and taking it. Includes physical and moral courage.\n\nSSB Task: Big obstacles, Interview (admitting mistakes).\nDevelopment: Speak up for what is right, face physical challenges.",
                    "Stamina" to "Endurance to withstand long-term physical and mental stress.\n\nSSB Task: 5-day schedule, GTO day.\nDevelopment: Regular physical exercise and long study sessions."
                )
            )
        )
    }
}
