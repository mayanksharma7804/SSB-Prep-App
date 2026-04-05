package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class ProcedureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procedure);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Day 0: Introduction & Reporting
        setupTask(R.id.ssbIntroButton, "Introduction & Reporting", 
            "The journey begins with reporting at the specified Railway Station or Selection Center.\n\n" +
            "Steps:\n" +
            "1. Reception: Candidates are picked up from the station.\n" +
            "2. Documentation: Verification of original certificates and photographs.\n" +
            "3. Opening Address: Briefing by the Duty Officer about the rules and schedule.\n" +
            "4. Chest Number Allotment: Your unique identity for the next 5 days.",
            "• Punctuality: Reach the station well before the reporting time.\n" +
            "• Documents: Keep all originals and photocopies organized in a folder.\n" +
            "• First Impression: Maintain a neat appearance and disciplined behavior from the moment you arrive.");

        // Day 1: Screening (Stage I)
        setupTask(R.id.day1Button, "Day 1: Screening (Stage I)", 
            "Stage I is the elimination round. It consists of two major tests: OIR and PPDT.\n\n" +
            "1. OIR (Officer Intelligence Rating): Verbal and Non-Verbal reasoning booklets.\n" +
            "2. PPDT (Picture Perception & Description Test):\n" +
            "   - View hazy picture (30 sec)\n" +
            "   - Write story (4 min)\n" +
            "   - Individual Narration (1 min)\n" +
            "   - Group Discussion (15-20 min)\n\n" +
            "Results: Announced on the same day. Screened-in candidates stay for Stage II.",
            "• Narrative: Be crisp, clear, and loud enough.\n" +
            "• GD: Focus on achieving a 'Group Story' rather than individual dominance.\n" +
            "• OIR: Try to solve as many questions as possible with high accuracy.");

        // Day 2: Psychology (Stage II)
        setupTask(R.id.day2Button, "Day 2: Psychology Test", 
            "Tests the subconscious mind through 4 battery of tests:\n\n" +
            "1. TAT (Thematic Apperception Test): 11+1 hazy/blank pictures for story writing.\n" +
            "2. WAT (Word Association Test): 60 words, 15 seconds each to write a sentence.\n" +
            "3. SRT (Situation Reaction Test): 60 real-life situations, 30 minutes to respond.\n" +
            "4. SD (Self Description): Writing opinions of Parents, Teachers, Friends, and Self.",
            "• TAT: Write positive, constructive stories where the hero solves a problem.\n" +
            "• WAT: Use natural, spontaneous responses. Avoid cramming universal truths.\n" +
            "• SRT: Be practical and finish the action. Don't just plan, act.\n" +
            "• SD: Be honest and prepare this in advance.");

        // Day 3-4: GTO & Interview
        setupTask(R.id.day34Button, "Day 3-4: GTO & Personal Interview", 
            "Group Testing Officer (GTO) Tasks (Outdoor/Indoor):\n" +
            "• GD, GPE (Group Planning), PGT (Progressive Group Task).\n" +
            "• HGT (Half Group Task), IOT (Individual Obstacles), Command Task.\n" +
            "• Snake Race, Final Group Task.\n\n" +
            "Personal Interview:\n" +
            "A one-on-one conversation with the President/Deputy President of the board focusing on your PIQ (Personal Information Questionnaire).",
            "• GTO: Work as a team. Don't look at the GTO during tasks. Offer logic, not just muscle.\n" +
            "• Interview: Be truthful. If you don't know an answer, admit it politely. Maintain a smile and eye contact.");

        // Day 5: Conference
        setupTask(R.id.day5Button, "Day 5: Conference & Final Results", 
            "The final assessment where all three assessors (Psychologist, GTO, Interviewing Officer) discuss your performance.\n\n" +
            "Process:\n" +
            "1. Closing Address by the Board President.\n" +
            "2. Individual Conference: You enter a room with all officers in uniform. They may ask general questions about your stay.\n" +
            "3. Results: Final recommendation announced.",
            "• Confidence: Walk in with a smile and wish the President.\n" +
            "• Stay Calm: Don't overthink the questions asked in the conference.");

        // Specialized: PABT/CPSS
        setupTask(R.id.pabtButton, "PABT / CPSS (Pilot Testing)", 
            "Pilot Aptitude Battery Test (PABT) is a once-in-a-lifetime test for candidates opting for the Flying Branch.\n\n" +
            "Components:\n" +
            "1. INSB (Instrument Battery Test): Reading dials and instruments.\n" +
            "2. Machine Test: Hand-eye-foot coordination using a joystick and pedals.\n\n" +
            "Modern System: Now replaced by CPSS (Computerised Pilot Selection System) which tests cognitive skills and psychomotor abilities.",
            "• Listening: Pay extreme attention to the briefing/instructions.\n" +
            "• Calmness: Don't panic if you make a mistake on the machine; keep focusing on the current task.");

        // ICG Selection
        setupTask(R.id.coastGuardButton, "Indian Coast Guard (PSB/FSB)", 
            "ICG follows a two-stage selection process similar to SSB.\n\n" +
            "Stage I: PSB (Preliminary Selection Board):\n" +
            "- Mental Ability Test (Section I)\n" +
            "- PPDT (Section II)\n\n" +
            "Stage II: FSB (Final Selection Board):\n" +
            "Held at Noida/Mumbai/Chennai. Includes Psychological tests, GTO, Interview, and Board Conference.",
            "• Preparation: Treat PSB as a rigorous screening. Your OIR (Mental Ability) score is very important in ICG.");
    }

    private void setupTask(int id, String title, String desc, String tips) {
        CardView card = findViewById(id);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, TaskDetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("description", desc);
                intent.putExtra("tips", tips);
                startActivity(intent);
            });
        }
    }
}
