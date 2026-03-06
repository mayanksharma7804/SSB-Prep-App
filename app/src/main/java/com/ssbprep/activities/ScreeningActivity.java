package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class ScreeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        setupTask(R.id.ppdtgdButton, "PPDT & GD", 
            "Picture Perception and Description Test followed by a group discussion on the stories written.",
            "• Observe the picture carefully for 30 seconds (Character, Age, Mood, Setting).\n• Write a positive and realistic story in 1 minute.\n• In GD, be calm and try to contribute to a common group story without being aggressive.");

        setupTask(R.id.oirButton, "OIR Test", 
            "Officer Intelligence Rating test consisting of Verbal and Non-Verbal reasoning.",
            "• Speed and accuracy are vital; solve as many as possible.\n• Practice number series, analogies, and pattern completion.\n• Don't get stuck on a single difficult question; move on.");
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
