package com.ssbprep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.ssbprep.R;

public class GroundTasksActivity extends AppCompatActivity {

    private static final String GTO_RULES = "\n\nEssential Rules for Obstacle Tasks:\n" +
            "• Rule 1: In the direction we want to extend, the structure ahead is always the fulcrum.\n" +
            "• Rule 2: All projections in the GTO Ground are effort.\n" +
            "• Rule 3: For stability, the lateral distance between fulcrum and effort should be at least 6-9 inches.\n" +
            "• Rule 4: Tie rope low/tight for effort, and high/tight for fulcrum.\n" +
            "• Rule 5: All cuts in the GTO ground are effort.\n" +
            "• Rule 6: Last person idea comes from the opposite direction.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_tasks);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Indoor Tasks
        setupTask(R.id.gdButton, "Group Discussion (GD)", 
            "Two rounds of discussion on social or current issues, including International Relations.",
            "• Topics: Social Issues, Current Affairs, International Relations.\n• Be a good listener as much as a speaker.\n• Don't shout or dominate; use logical points.");

        setupTask(R.id.gpeButton, "Group Planning Exercise (GPE)", 
            "A map-based exercise where the group solves multiple problems through briefing, individual planning, and group discussion.",
            "• Phases: Briefing, Individual Planning, Group Discussion, Final Conclusion.\n• Prioritize problems (Life > Property > Other).\n• Give practical solutions with time and distance calculations.");

        // Special handling for Lecturette to show categorized topics
        CardView lecCard = findViewById(R.id.lecButton);
        if (lecCard != null) {
            lecCard.setOnClickListener(v -> {
                startActivity(new Intent(this, LecturetteTopicsActivity.class));
            });
        }

        // Outdoor Tasks
        setupTask(R.id.pgtButton, "Progressive Group Task (PGT)", 
            "Four obstacles in increasing order of difficulty. The group must move together using helping materials like Plank (Phattah), Balla (Log), and Rope.",
            "• Rules: Rigidity, Infinity, Color (White/Blue/Red), Distance, and Group Rule." + GTO_RULES);

        setupTask(R.id.hgtButton, "Half Group Task (HGT)", 
            "Similar to PGT but with the group split in half to allow closer observation by the GTO.",
            "• Difficulty: Moderate.\n• GTO watches you very closely here." + GTO_RULES);

        setupTask(R.id.gorButton, "Snake Race / Group Obstacle Race", 
            "A high-energy competitive race carrying a snake-like load over obstacles like Spider Web, Parallel Walls, Giant Slide, and Figure of 8.",
            "• Keep the energy high with group cheers.\n• Help the physically weaker members cross first.\n• Never let the snake touch the ground.");

        setupTask(R.id.ioButton, "Individual Obstacles (IO)", 
            "Crossing 10 obstacles individually in 3 minutes to test stamina and courage.",
            "• Obstacles: Single Ditch (1pt), Double Ditch (2pt), Screen Jump (3pt), Wall Jump (4pt), Tarzan Jump, Burma Bridge, Tiger Leap, Double Platform Jump, Rope Climbing.\n• Plan your path to minimize running distance.\n• Don't waste time on an obstacle if you fail; move to the next.");

        setupTask(R.id.ctButton, "Command Task (CT)", 
            "You are appointed as Commander and must choose two subordinates to solve a task (Start-Finish, Circular, or Semi-Circular type).",
            "• Types: Start-Finish, Circular Type 1 & 2, Semi-Circular." + GTO_RULES);

        setupTask(R.id.fgtButton, "Final Group Task (FGT)", 
            "One last obstacle for the entire group to perform together, usually similar to PGT but only one obstacle.",
            "• Show that you are still energetic and a team player." + GTO_RULES);
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
