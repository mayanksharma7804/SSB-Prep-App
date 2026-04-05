package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class PsychologyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychology);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        setupTask(R.id.tatButton, "Thematic Apperception Test (TAT)", 
            "TAT is the first test in the Psychology series. It involves writing 12 stories based on 11 hazy pictures and 1 blank slide.\n\n" +
            "Essentials of a TAT Story:\n" +
            "• Hero: A central character (ideally your age) who leads the action.\n" +
            "• Structure: Introduction, Main Body (Action), and Conclusion.\n" +
            "• Theme: Should be realistic and optimistic.\n\n" +
            "Example:\n" +
            "Picture: A young man looking at a field.\n" +
            "Story: Rahul, an agriculture graduate, noticed declining crop yields in his village. He organized a meeting with local farmers, introduced modern soil testing techniques, and collaborated with the district agri-office for subsidies on organic fertilizers. Within a year, the village saw a 20% increase in yield, making Rahul a respected figure in his community.",
            "• Observation: Observe the background and characters' expressions.\n" +
            "• Perspective: Past (what led to it), Present (the action), and Future (positive outcome).\n" +
            "• Timing: Aim for 10-12 lines in 3.5 minutes.\n" +
            "• Authenticity: Write your first natural thought; don't use coached stories.");

        setupTask(R.id.watButton, "Word Association Test (WAT)", 
            "Writing 60 sentences on 60 words shown for 15 seconds each. It tests your subconscious reactions.\n\n" +
            "Examples:\n" +
            "• Success: Hard work leads to success.\n" +
            "• Fear: Knowledge overcomes fear.\n" +
            "• Country: Serving the country is a matter of pride.\n" +
            "• Failure: Failure is a stepping stone to success.",
            "• Write short, crisp sentences reflecting your personality.\n" +
            "• Avoid 'should' or 'must'; show action instead.\n" +
            "• Use first-person perspective sparingly.\n" +
            "• If you miss a word, move to the next immediately.");

        setupTask(R.id.srtButton, "Situation Reaction Test (SRT)", 
            "Responding to 60 real-life situations in 30 minutes. It checks your common sense and decision-making.\n\n" +
            "Example:\n" +
            "Situation: He was going for an exam and saw a road accident.\n" +
            "Reaction: Provided first aid, called for an ambulance, informed the victim's family, and then went to give his exam on time.",
            "• Be spontaneous but socially responsible.\n" +
            "• Complete the action; don't just say 'I will solve it'.\n" +
            "• Be realistic; don't act like a superhero.\n" +
            "• Speed is key; aim for at least 45+ situations.");

        setupTask(R.id.sdtButton, "Self Description Test (SDT)", 
            "Writing opinions of parents, teachers, friends, and self-opinion. It shows how well you know yourself.\n\n" +
            "Sections:\n" +
            "1. Parents' Opinion\n" +
            "2. Teachers'/Employers' Opinion\n" +
            "3. Friends'/Colleagues' Opinion\n" +
            "4. Self Opinion\n" +
            "5. Qualities you wish to develop",
            "• Be honest and mention both strengths and genuine improvements.\n" +
            "• Don't use generic adjectives; use instances or traits.\n" +
            "• Prepare this in advance as the questions are fixed.\n" +
            "• Ensure your SDT aligns with your PIQ form.");
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
