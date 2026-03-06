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
            "Writing 12 stories based on 11 pictures and 1 blank slide shown for 30 seconds each.",
            "• Make the hero around your age and give him/her a positive role.\n• Structure: What led to the situation, what is happening now, and what is the outcome.\n• Avoid 'preachy' or 'miraculous' endings; keep it realistic and problem-solving oriented.");

        setupTask(R.id.watButton, "Word Association Test (WAT)", 
            "Writing 60 sentences on 60 words shown for 15 seconds each.",
            "• Write short, crisp sentences that reflect your personality.\n• Avoid using 'should' or 'must'; show action instead.\n• If you miss a word, don't panic; move to the next immediately.");

        setupTask(R.id.srtButton, "Situation Reaction Test (SRT)", 
            "Responding to 60 real-life situations in 30 minutes.",
            "• Be spontaneous but socially responsible.\n• Complete the action; don't just say 'I will solve it'.\n• Efficiency and speed are key here.");

        setupTask(R.id.sdtButton, "Self Description Test (SDT)", 
            "Writing opinions of parents, teachers, friends, and self-opinion.",
            "• Be honest and mention both strengths and genuine improvements.\n• Don't make it look like a list of adjectives; use examples if possible.\n• Prepare this in advance as the questions are fixed.");
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
