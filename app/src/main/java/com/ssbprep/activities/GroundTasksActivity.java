package com.ssbprep.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ssbprep.R;

public class GroundTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_tasks);

        // Initialize all task buttons
        Button gdButton = findViewById(R.id.gdButton);
        Button gpeButton = findViewById(R.id.gpeButton);
        Button pgtButton = findViewById(R.id.pgtButton);
        Button gorButton = findViewById(R.id.gorButton);
        Button lecButton = findViewById(R.id.lecButton);
        Button hgtButton = findViewById(R.id.hgtButton);
        Button ioButton = findViewById(R.id.ioButton);
        Button ctButton = findViewById(R.id.ctButton);
        Button fgtButton = findViewById(R.id.fgtButton);
        Button backButton = findViewById(R.id.backButton);

        // Set click listeners
        gdButton.setOnClickListener(v -> handleTaskClick("Group Discussion"));
        gpeButton.setOnClickListener(v -> handleTaskClick("Group Planning Exercise"));
        pgtButton.setOnClickListener(v -> handleTaskClick("Progressive Group Task"));
        gorButton.setOnClickListener(v -> handleTaskClick("Group Obstacle Race"));
        lecButton.setOnClickListener(v -> handleTaskClick("Lecturette"));
        hgtButton.setOnClickListener(v -> handleTaskClick("Half Group Task"));
        ioButton.setOnClickListener(v -> handleTaskClick("Individual Obstacles"));
        ctButton.setOnClickListener(v -> handleTaskClick("Command Task"));
        fgtButton.setOnClickListener(v -> handleTaskClick("Final Group Task"));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void handleTaskClick(String taskName) {
        Toast.makeText(this, taskName + " selected!", Toast.LENGTH_SHORT).show();
        // TODO: Navigate to task details screen
    }
}
