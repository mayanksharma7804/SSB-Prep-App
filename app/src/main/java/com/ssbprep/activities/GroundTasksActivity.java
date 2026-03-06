package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class GroundTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_tasks);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        setupTask(R.id.gdButton, "Group Discussion", 
            "Two rounds of discussion on social or current issues.",
            "• Be a good listener as much as a speaker.\n• Don't shout or dominate; use logical points.\n• Try to initiate or conclude if you have good content.");

        setupTask(R.id.gpeButton, "Group Planning Exercise", 
            "A map-based exercise where the group solves multiple problems.",
            "• Prioritize problems (Life > Property > Other).\n• Give practical solutions with time and distance calculations.\n• Write a clear individual plan.");

        setupTask(R.id.pgtButton, "Progressive Group Task", 
            "Team task using planks, ballies, and ropes to cross obstacles.",
            "• Never stand on the obstacle itself.\n• Use materials logically (lever principle).\n• Work with the team, don't just order them.");

        setupTask(R.id.gorButton, "Snake Race (GOR)", 
            "High-energy race where the group carries a 'snake' through obstacles.",
            "• Keep the energy high with group cheers.\n• Help the physically weaker members cross first.\n• Never let the snake touch the ground.");

        setupTask(R.id.lecButton, "Lecturette", 
            "Individual 3-minute talk on a chosen topic.",
            "• Structure: Intro, Main Body, and Conclusion.\n• Maintain eye contact with the group, not just the GTO.\n• Speak clearly and confidently.");

        setupTask(R.id.hgtButton, "Half Group Task", 
            "Similar to PGT but with only half the group members.",
            "• GTO watches you very closely here.\n• Be more active since the competition is less.\n• Stay focused on the solution.");

        setupTask(R.id.ioButton, "Individual Obstacles", 
            "Crossing 10 obstacles individually in 3 minutes.",
            "• Plan your path to minimize running distance.\n• Don't waste time on an obstacle if you fail; move to the next.\n• Repeat high-point obstacles if time permits.");

        setupTask(R.id.ctButton, "Command Task", 
            "You are the commander and must solve a task with two subordinates.",
            "• Choose your subordinates wisely based on their strengths.\n• Give clear instructions and don't do all the work yourself.\n• Stay calm if the GTO changes the rules mid-task.");

        setupTask(R.id.fgtButton, "Final Group Task", 
            "One last team task to solve an obstacle.",
            "• Show that you are still energetic and a team player.\n• Execute the plan quickly.\n• Don't break any rules in a hurry.");
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
